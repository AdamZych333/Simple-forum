package forum.controller;

import forum.entity.User;
import forum.service.CommentService;
import forum.service.PostService;
import forum.service.TagService;
import forum.service.dto.CommentDTO;
import forum.service.dto.CreatedCommentDTO;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/api/posts")
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final PostService postService;
    private final TagService tagService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService,
                          TagService tagService,
                          CommentService commentService
    ) {
        this.postService = postService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Void> addPost(
            @RequestBody @Validated CreatedPostDTO createdPostDTO,
            @AuthenticationPrincipal User authenticatedUser
    ) throws URISyntaxException {
        log.debug("Request to save: post : {}", createdPostDTO);

        tagService.addTags(createdPostDTO.getTags());
        postService.save(createdPostDTO, authenticatedUser);

        return ResponseEntity.created(new URI("")).build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> addComment(
            @PathVariable final Long id,
            @RequestBody @Validated CreatedCommentDTO createdCommentDTO,
            @AuthenticationPrincipal User authenticatedUser
    ) throws URISyntaxException {
        log.debug("Request to save: comment {} in post {}", createdCommentDTO, id);

        commentService.save(createdCommentDTO, authenticatedUser, id);

        return ResponseEntity.created(new URI("")).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(
            @PathVariable final Long id
    ){
        log.debug("Request to get: post {}", id);

        PostDTO postDTO = postService.getPost(id);

        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getPostComments(
            @PathVariable final Long id
    ){
        log.debug("Request to get: post comments {}", id);

        List<CommentDTO> commentDTOS = commentService.getPostComments(id);

        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "dsc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
            ){
        log.debug("Request to get: posts");

        List<PostDTO> postDTOS = postService.getPosts(query, sortBy, order, Math.max(page, 0), Math.max(pageSize, 1));

        return ResponseEntity.ok(postDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @RequestBody @Validated CreatedPostDTO postDTO,
            @PathVariable final Long id,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to update: post {} to {}", id, postDTO);

        tagService.addTags(postDTO.getTags());
        postService.updatePost(postDTO, id, authenticatedUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable final Long id,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to delete: post {}", id);

        postService.deletePost(id, authenticatedUser);

        return ResponseEntity.noContent().build();
    }
}

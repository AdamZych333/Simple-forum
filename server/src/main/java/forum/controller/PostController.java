package forum.controller;

import forum.entity.User;
import forum.service.FollowService;
import forum.service.PostService;
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
    private final FollowService followService;

    @Autowired
    public PostController(PostService postService,
                          FollowService followService
    ) {
        this.postService = postService;
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> addPost(
            @RequestBody @Validated CreatedPostDTO createdPostDTO,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        log.debug("Request to save: post : {}", createdPostDTO);

        PostDTO postDTO = postService.save(createdPostDTO, authenticatedUser);

        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to get: post {}", id);

        PostDTO postDTO = postService.getPost(id);
        followService.updateLastVisitTime(id, authenticatedUser);

        return ResponseEntity.ok(postDTO);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "dsc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @AuthenticationPrincipal User authenticatedUser
            ){
        log.debug("Request to get: posts");

        List<PostDTO> postDTOS = postService.getPosts(query, sortBy, order, Math.max(page, 0), pageSize);

        return ResponseEntity.ok(postDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable final Long id,
            @RequestBody @Validated CreatedPostDTO postDTO,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to update: post {} to {}", id, postDTO);

        postService.updatePost(id, postDTO, authenticatedUser);

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

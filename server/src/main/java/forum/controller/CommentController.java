package forum.controller;

import forum.entity.Post;
import forum.entity.User;
import forum.service.CommentService;
import forum.service.dto.CommentDTO;
import forum.service.dto.CreatedCommentDTO;
import forum.service.dto.CreatedPostDTO;
import forum.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/api/posts/{postID}/comments")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getPostComments(
            @PathVariable("postID") final Optional<Post> post
    ){
        log.debug("Request to get: post comments {}", post);

        List<CommentDTO> commentDTOS = commentService.getPostComments(
                post.orElseThrow(
                        () -> new EntityNotFoundException("Post with requested if doesn't exist.")
                ).getId()
        );

        return ResponseEntity.ok(commentDTOS);
    }

    @PostMapping
    public ResponseEntity<Void> addComment(
            @PathVariable("postID") final Optional<Post> post,
            @RequestBody @Validated CreatedCommentDTO createdCommentDTO,
            @AuthenticationPrincipal User authenticatedUser
    ) throws URISyntaxException {
        log.debug("Request to save: comment {} in post {}", createdCommentDTO, post);

        commentService.save(createdCommentDTO, authenticatedUser,
                post.orElseThrow(
                        () -> new EntityNotFoundException("Post with requested if doesn't exist.")
                )
        );

        return ResponseEntity.created(new URI("")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("postID") final Optional<Post> post,
            @PathVariable final Long id,
            @RequestBody @Validated CreatedCommentDTO commentDTO,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to update: comment {} to {}", id, commentDTO);

        commentService.updateComment(id, commentDTO, authenticatedUser,
                post.orElseThrow(
                        () -> new EntityNotFoundException("Post with requested if doesn't exist.")
                ).getId()
        );

        return ResponseEntity.noContent().build();
    }
}

package forum.controller;

import forum.entity.Post;
import forum.entity.User;
import forum.service.CommentService;
import forum.service.dto.CommentDTO;
import forum.service.dto.CreatedCommentDTO;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.UpdateCommentDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.exception.InvalidParentIdException;
import forum.service.security.UserRightsChecker;
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

        if(!post.isPresent()){
            throw new EntityNotFoundException("Post with requested id doesn't exists.");
        }

        List<CommentDTO> commentDTOS = commentService.getPostComments(post.get().getId());

        return ResponseEntity.ok(commentDTOS);
    }

    @PostMapping
    public ResponseEntity<Void> addComment(
            @PathVariable("postID") final Optional<Post> post,
            @RequestBody @Validated CreatedCommentDTO createdCommentDTO,
            @AuthenticationPrincipal User authenticatedUser
    ) throws URISyntaxException {
        log.debug("Request to save: comment {} in post {}", createdCommentDTO, post);

        if(!post.isPresent()){
            throw new EntityNotFoundException("Post with requested id doesn't exists.");
        }

        commentService.save(createdCommentDTO, authenticatedUser, post.get());

        return ResponseEntity.created(new URI("")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable("postID") final Optional<Post> post,
            @PathVariable final Long id,
            @RequestBody @Validated UpdateCommentDTO updateCommentDTO,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to update: comment {} to {}", id, updateCommentDTO);

        if(!post.isPresent()){
            throw new EntityNotFoundException("Post with requested id doesn't exists.");
        }

        commentService.updateComment(id, post.get().getId(), updateCommentDTO, authenticatedUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("postID") final Optional<Post> post,
            @PathVariable final Long id,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to delete: comment {}", id);

        if(!post.isPresent()){
            throw new EntityNotFoundException("Post with requested id doesn't exists.");
        }

        commentService.deleteComment(id, post.get().getId(), authenticatedUser);

        return ResponseEntity.noContent().build();
    }
}

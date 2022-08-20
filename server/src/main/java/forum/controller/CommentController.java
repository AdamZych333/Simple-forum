package forum.controller;

import forum.entity.User;
import forum.service.CommentService;
import forum.service.dto.CommentDTO;
import forum.service.dto.CreatedCommentDTO;
import forum.service.dto.UpdateCommentDTO;
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
@RequestMapping(path="/api")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postID}/comments")
    public ResponseEntity<List<CommentDTO>> getPostComments(
            @PathVariable final Long postID
    ){
        log.debug("Request to get: post {} comments", postID);

        List<CommentDTO> commentDTOS = commentService.getPostComments(postID);

        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> searchComments(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(required = false) Long userID,
            @RequestParam(required = false) Long postID
    ){
        log.debug("Request to search: comments");

        List<CommentDTO> commentDTOS = commentService.searchComments(query, userID, postID);

        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/posts/{postID}/comments/{id}")
    public ResponseEntity<CommentDTO> getPostComment(
            @PathVariable final Long postID,
            @PathVariable final Long id
    ){
        log.debug("Request to get: post {} comment {}", postID, id);

        CommentDTO commentDTO = commentService.getPostComment(id, postID);

        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping("/posts/{postID}/comments")
    public ResponseEntity<Void> addComment(
            @PathVariable final Long postID,
            @RequestBody @Validated CreatedCommentDTO createdCommentDTO,
            @AuthenticationPrincipal User authenticatedUser
    ) throws URISyntaxException {
        log.debug("Request to save: comment {} in post {}", createdCommentDTO, postID);

        commentService.save(createdCommentDTO, authenticatedUser, postID);

        return ResponseEntity.created(new URI("")).build();
    }

    @PutMapping("/posts/{postID}/comments/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable final long postID,
            @PathVariable final Long id,
            @RequestBody @Validated UpdateCommentDTO updateCommentDTO,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to update: comment {} to {}", id, updateCommentDTO);

        commentService.updateComment(id, postID, updateCommentDTO, authenticatedUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{postID}/comments/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable final long postID,
            @PathVariable final Long id,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to delete: comment {}", id);

        commentService.deleteComment(id, postID, authenticatedUser);

        return ResponseEntity.noContent().build();
    }
}

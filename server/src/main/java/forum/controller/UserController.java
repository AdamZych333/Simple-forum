package forum.controller;

import forum.entity.User;
import forum.service.CommentService;
import forum.service.PostService;
import forum.service.UserService;
import forum.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public UserController(UserService userService,
                          PostService postService,
                          CommentService commentService
    ) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/{id}/admin")
    public ResponseEntity<Void> makeAdmin(
            @PathVariable final Long id
    ) throws URISyntaxException {
        log.debug("Request to make admin: user {}", id);

        userService.makeAdmin(id);

        return ResponseEntity.created(new URI("")).build();
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        log.debug("Request to get: users.");

        List<UserDTO> userDTOS = userService.getUsers();

        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(
            @PathVariable final Long id
    ){
        log.debug("Request to get: user {}", id);

        UserDTO userDTO = userService.getUserById(id);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getUserPosts(
            @PathVariable final Long id,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "dsc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        log.debug("Request to get: posts of user {}", id);

        List<PostDTO> postDTOS = postService.getUserPosts(id, sortBy, order, Math.max(page, 0), pageSize);

        return ResponseEntity.ok(postDTOS);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getUserComments(
            @PathVariable final Long id
    ){
        log.debug("Request to get: comments of user {}", id);

        List<CommentDTO> commentDTOS = commentService.getUserComments(id);

        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/{id}/follows")
    public ResponseEntity<List<FollowedPostDTO>> getFollowedPosts(
            @PathVariable final Long id
    ){
        log.debug("Request to get: posts of user {}", id);

        List<FollowedPostDTO> postDTOS = postService.getFollowedPosts(id);

        return ResponseEntity.ok(postDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable final Long id,
            @RequestBody UpdateUserDTO updateUserDTO,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to update: user {} by {}", id, authenticatedUser);

        userService.updateUser(id, updateUserDTO, authenticatedUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable final Long id,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to delete: user {} by {}", id, authenticatedUser);

        userService.deleteUser(id, authenticatedUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/admin")
    public ResponseEntity<Void> removeAdmin(
            @PathVariable final Long id
    ) {
        log.debug("Request to remove admin: user {}", id);

        userService.removeAdmin(id);

        return ResponseEntity.noContent().build();
    }
}

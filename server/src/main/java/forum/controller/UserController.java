package forum.controller;

import forum.entity.User;
import forum.service.PostService;
import forum.service.UserService;
import forum.service.dto.PostDTO;
import forum.service.dto.UpdateUserDTO;
import forum.service.dto.UserDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.security.UserRightsChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService,
                          PostService postService
    ) {
        this.userService = userService;
        this.postService = postService;
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

        List<PostDTO> postDTOS = postService.getUserPosts(id, sortBy, order, Math.max(page, 0), Math.max(pageSize, 1));

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
}

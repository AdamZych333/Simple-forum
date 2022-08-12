package forum.controller;

import forum.service.PostService;
import forum.service.UserService;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import forum.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> addPost(
            @RequestBody @Validated CreatedPostDTO createdPostDTO,
            Authentication authentication
    ) throws URISyntaxException {
        log.debug("Request to save new post : {}", createdPostDTO);

        UserDTO authenticatedUser = userService.getUserByEmail(authentication.getName());
        createdPostDTO.setUserId(authenticatedUser.getId());

        postService.save(createdPostDTO);

        return ResponseEntity.created(new URI("")).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(
            @PathVariable final Long id
    ){
        log.debug("Request to get post {}", id);

        PostDTO postDTO = postService.getPost(id);

        return ResponseEntity.ok(postDTO);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "dsc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
            ){
        log.debug("Request to get posts");

        List<PostDTO> postDTOS = postService.getPosts(query, sortBy, order, Math.abs(page), Math.abs(pageSize));

        return ResponseEntity.ok(postDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @RequestBody @Validated CreatedPostDTO postDTO,
            @PathVariable final Long id,
            Authentication authentication
    ){
        log.debug("Request to update post {}: {}", id, postDTO);

        UserDTO authenticatedUser = userService.getUserByEmail(authentication.getName());
        postService.updatePost(postDTO, id, authenticatedUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable final Long id,
            Authentication authentication
    ){
        log.debug("Request to delete post {}", id);

        UserDTO authenticatedUser = userService.getUserByEmail(authentication.getName());
        postService.deletePost(id, authenticatedUser);

        return ResponseEntity.noContent().build();
    }
}

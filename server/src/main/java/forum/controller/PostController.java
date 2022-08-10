package forum.controller;

import forum.service.PostService;
import forum.service.UserService;
import forum.service.dto.CreatedPostDTO;
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
}
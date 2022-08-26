package forum.controller;

import forum.entity.User;
import forum.service.FollowService;
import forum.service.dto.FollowsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping(path="/api/posts/{postID}/follows")
public class FollowController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<Void> follow(
            @PathVariable Long postID,
            @AuthenticationPrincipal User authenticatedUser
    ) throws URISyntaxException {
        log.debug("Request to save: follow {} by {}", postID, authenticatedUser);

        followService.follow(postID, authenticatedUser);

        return ResponseEntity.created(new URI("")).build();
    }

    @GetMapping
    public ResponseEntity<FollowsDTO> getFollows(
            @PathVariable Long postID,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        log.debug("Request to save: follow {} by {}", postID, authenticatedUser);

        FollowsDTO followsDTO = followService.getFollows(postID, authenticatedUser.getId());

        return ResponseEntity.ok(followsDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> unfollow(
            @PathVariable Long postID,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to delete: follow {} by {}", postID, authenticatedUser);

        followService.unfollow(postID, authenticatedUser);

        return ResponseEntity.noContent().build();
    }
}

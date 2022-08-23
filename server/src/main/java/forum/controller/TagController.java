package forum.controller;

import forum.entity.User;
import forum.service.PostService;
import forum.service.TagService;
import forum.service.dto.PostDTO;
import forum.service.dto.TagDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/api/tags")
public class TagController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final TagService tagService;
    private final PostService postService;

    @Autowired
    public TagController(TagService tagService,
                         PostService postService
    ) {
        this.tagService = tagService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize
    ){
        log.debug("Request to get: tags page {} pageSize {}.", page, pageSize);

        List<TagDTO> tags = tagService.getTags(page, pageSize);

        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getPosts(
            @PathVariable final Long id,
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "dsc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @AuthenticationPrincipal User authenticatedUser
    ){
        log.debug("Request to get: posts with tag {}", id);

        List<PostDTO> postDTOS = postService.getPostsByTag(id, query, sortBy, order, Math.max(page, 0), pageSize);
        postDTOS.forEach(p -> {
            postService.addVotesAndFollows(p, authenticatedUser.getId());
        });

        return ResponseEntity.ok(postDTOS);
    }
}

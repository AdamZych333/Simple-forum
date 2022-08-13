package forum.controller;

import forum.service.TagService;
import forum.service.dto.TagDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/api/tags")
public class TagController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
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
}

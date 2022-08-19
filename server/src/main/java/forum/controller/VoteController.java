package forum.controller;

import forum.entity.User;
import forum.service.VoteService;
import forum.service.dto.CreatedVoteDTO;
import forum.service.dto.VoteDTO;
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
@RequestMapping(path="/api/posts/{postID}/votes")
public class VoteController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Void> vote(
            @PathVariable Long postID,
            @RequestBody CreatedVoteDTO vote,
            @AuthenticationPrincipal User authenticatedUser
    ) throws URISyntaxException {
        log.debug("Request to save: vote up on {} by {}", postID, authenticatedUser);

        voteService.vote(vote.getType().name, postID, authenticatedUser);

        return ResponseEntity.created(new URI("")).build();
    }

    @GetMapping
    public ResponseEntity<List<VoteDTO>> getVotes(
            @PathVariable Long postID
    ) {
        log.debug("Request to get: vote up on {}", postID);

        List<VoteDTO> voteDTOList = voteService.getVotes(postID);

        return ResponseEntity.ok(voteDTOList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVote(
            @PathVariable Long postID,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        log.debug("Request to delete: vote on {} by {}", postID, authenticatedUser);

        voteService.deleteVote(postID, authenticatedUser);

        return ResponseEntity.noContent().build();
    }
}
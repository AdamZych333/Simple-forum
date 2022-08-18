package forum.service;

import forum.config.Constants;
import forum.entity.Post;
import forum.entity.User;
import forum.entity.Vote;
import forum.repository.PostRepository;
import forum.repository.VoteRepository;
import forum.service.dto.VoteDTO;
import forum.service.exception.EntityAlreadyExistsException;
import forum.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class VoteService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;


    @Autowired
    public VoteService(VoteRepository voteRepository,
                       PostRepository postRepository
    ) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
    }

    public List<VoteDTO> getVotes(Long postID){
        log.debug("Fetching: votes of post {}", postID);

        if(!postRepository.existsById(postID)) {
            throw new EntityNotFoundException("Post with requesting id doesn't exist");
        }

        List<VoteDTO> voteDTOS = new ArrayList<>();
        for(Constants.Vote type : Constants.Vote.values()){
            Set<Vote> votes = voteRepository.findAllByPost_IdAndType(postID, type.name);
            VoteDTO voteDTO = new VoteDTO();
            voteDTO.setType(type);
            voteDTO.setCount(votes.size());
            voteDTOS.add(voteDTO);
        }

        return voteDTOS;

    }

    public void vote(String type, Long postID, User authenticatedUser){
        log.debug("Creating: vote {} by {} on post {}", type, authenticatedUser, postID);

        Post post = postRepository.findById(postID)
                .orElseThrow(() -> new EntityNotFoundException("Post with requesting id doesn't exist"));
        Vote vote = voteRepository.findByUser_IdAndPost_Id(authenticatedUser.getId(), postID)
                .orElse(new Vote(authenticatedUser, post));
        vote.setType(type);

        voteRepository.save(vote);
    }

    public void deleteVote(Long postID, User authenticatedUser){
        log.debug("Deleting: vote by {} on post {}", authenticatedUser, postID);

        Vote vote = voteRepository.findByUser_IdAndPost_Id(authenticatedUser.getId(), postID)
                .orElseThrow(() -> new EntityNotFoundException("This user haven't rated the post yet."));

        voteRepository.delete(vote);
    }
}

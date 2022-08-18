package forum.service;

import forum.entity.Follow;
import forum.entity.Post;
import forum.entity.User;
import forum.repository.FollowRepository;
import forum.repository.PostRepository;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.PostAlreadyFollowedException;
import forum.service.exception.PostNotFollowedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FollowService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    @Autowired
    public FollowService(FollowRepository followRepository,
                         PostRepository postRepository
    ) {
        this.followRepository = followRepository;
        this.postRepository = postRepository;
    }

    public void follow(Long postID, User authenticatedUser){
        log.debug("Creating: follow {} by {}", postID, authenticatedUser);

        Post post = postRepository.findById(postID)
                .orElseThrow(() -> new EntityNotFoundException("Requested post not found."));
        Optional<Follow> follow = followRepository.findByPost_IdAndUser_Id(postID, authenticatedUser.getId());
        if(follow.isPresent()) throw new PostAlreadyFollowedException("This post is already followed by requesting user.");

        Follow newFollow = new Follow();
        newFollow.setPost(post);
        newFollow.setUser(authenticatedUser);
        newFollow.setLastVisitAt(new Timestamp(System.currentTimeMillis()));

        followRepository.save(newFollow);
    }

    public void unfollow(Long postID, User authenticatedUser){
        log.debug("Creating: unfollow {} by {}", postID, authenticatedUser);

        if(!postRepository.existsById(postID)){
            throw new EntityNotFoundException("Requested post not found");
        }
        Optional<Follow> follow = followRepository.findByPost_IdAndUser_Id(postID, authenticatedUser.getId());
        if(!follow.isPresent()) throw new PostNotFollowedException("This post is not followed by requesting user.");

        followRepository.delete(follow.get());
    }

    public void updateLastVisitTime(Long postID, User authenticatedUser){
        log.debug("Updating: follow {} by {}", postID, authenticatedUser);

        Optional<Follow> follow = followRepository.findByPost_IdAndUser_Id(postID, authenticatedUser.getId());
        if(!follow.isPresent()) return;
        follow.get().setLastVisitAt(new Timestamp(System.currentTimeMillis()));

        followRepository.save(follow.get());
    }
}

package forum.service;

import forum.entity.Follow;
import forum.entity.Post;
import forum.entity.User;
import forum.repository.FollowRepository;
import forum.repository.PostRepository;
import forum.repository.UserRepository;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.FollowedPostDTO;
import forum.service.dto.PostDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.mapper.PostMapper;
import forum.service.security.UserRightsChecker;
import forum.utils.SearchFiltersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final TagService tagService;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final VoteService voteService;

    @Autowired
    public PostService(PostMapper postMapper,
                       PostRepository postRepository,
                       TagService tagService,
                       UserRepository userRepository,
                       FollowRepository followRepository,
                       VoteService voteService
    ) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.voteService = voteService;
    }

    public void save(CreatedPostDTO createdPostDTO, User authenticatedUser){
        log.debug("Saving: post {}", createdPostDTO);

        Post post = postMapper.toPostFromCreatedPostDTO(createdPostDTO);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setLastModificationAt(new Timestamp(System.currentTimeMillis()));
        post.setUser(authenticatedUser);

        Post newPost = postRepository.save(post);
        tagService.updateTags(createdPostDTO.getTags(), newPost);
    }

    public PostDTO getPost(Long id){
        log.debug("Fetching: post {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requested post not found"));

        PostDTO postDTO = postMapper.toDto(post);
        postDTO.setVotes(voteService.getVotes(id));

        return postDTO;
    }

    public List<PostDTO> getPosts(String query,
                                  String sortBy,
                                  String order,
                                  int page,
                                  int pageSize
    ){
        log.debug("Fetching: posts page {} pageSize {}", page, pageSize);

        List<String> allowedParams = Arrays.asList("createdAt", "title", "content");

        List<Post> posts;
        if(pageSize > 0) {
            posts = postRepository.findAllByContentContainingOrTitleContaining(query, query,
                    SearchFiltersUtil.getPageRequest(sortBy, order, page, pageSize, allowedParams)
            );
        }else{
            posts = postRepository.findAllByContentContainingOrTitleContaining(query, query,
                    SearchFiltersUtil.getSort(sortBy, order, allowedParams)
            );
        }

        List<PostDTO> postDTOS = postMapper.toDto(posts);
        for(PostDTO postDTO : postDTOS){
            postDTO.setVotes(voteService.getVotes(postDTO.getId()));
        }

        return postDTOS;
    }

    public List<PostDTO> getUserPosts(Long id,
                                       String sortBy,
                                       String order,
                                       int page,
                                       int pageSize
    ){
        log.debug("Fetching: posts of user {} page {} pageSize {}", id, page, pageSize);

        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("User with requested id doesn't exist");
        }

        List<String> allowedParams = Arrays.asList("createdAt", "title", "content");

        List<Post> posts;
        if(pageSize > 0) {
            posts = postRepository.findAllByUser_Id(id,
                    SearchFiltersUtil.getPageRequest(sortBy, order, page, pageSize, allowedParams)
            );
        }else{
            posts = postRepository.findAllByUser_Id(id,
                    SearchFiltersUtil.getSort(sortBy, order, allowedParams)
            );
        }

        List<PostDTO> postDTOS = postMapper.toDto(posts);
        for(PostDTO postDTO : postDTOS){
            postDTO.setVotes(voteService.getVotes(postDTO.getId()));
        }

        return postDTOS;
    }

    public List<FollowedPostDTO> getFollowedPosts(Long userID){
        log.debug("Fetching: posts followed by user {}", userID);
        if(!userRepository.existsById(userID)){
            throw new EntityNotFoundException("User with requested id doesn't exist");
        }
        List<Follow> follows = followRepository.findAllByUser_Id(userID);
        List<FollowedPostDTO> followedPosts = new ArrayList<>();
        for(Follow follow : follows){
            FollowedPostDTO followedPostDTO = new FollowedPostDTO(postMapper.toDto(follow.getPost()));
            long newActivity = follow.getPost().getComments().stream()
                    .filter(e -> e.getCreatedAt().compareTo(follow.getLastVisitAt()) > 0)
                    .count();
            followedPostDTO.setNewActivity(newActivity);
            followedPostDTO.setVotes(voteService.getVotes(followedPostDTO.getId()));
            followedPosts.add(followedPostDTO);
        }

        return followedPosts;
    }

    public void deletePost(Long id, User authenticatedUser){
        log.debug("Deleting: post {}", id);

        Post post = getEditablePost(id, authenticatedUser);

        postRepository.delete(post);
    }

    public void updatePost(Long id, CreatedPostDTO createdPostDTO, User authenticatedUser){
        log.debug("Updating: post {} to {}", id, createdPostDTO);

        Post post = getEditablePost(id, authenticatedUser);
        post.setContent(createdPostDTO.getContent());
        post.setTitle(createdPostDTO.getTitle());
        post.setLastModificationAt(new Timestamp(System.currentTimeMillis()));
        tagService.updateTags(createdPostDTO.getTags(), post);

        postRepository.save(post);
    }

    private Post getEditablePost(Long id, User authenticatedUser){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requested post not found"));
        if(!UserRightsChecker.hasRights(authenticatedUser, post.getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to update this post.");
        }

        return post;
    }
}

package forum.service;

import forum.entity.Post;
import forum.entity.User;
import forum.repository.PostRepository;
import forum.repository.UserRepository;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import forum.service.dto.UserDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.exception.PostAlreadyFollowedException;
import forum.service.exception.PostNotFollowedException;
import forum.service.mapper.PostMapper;
import forum.service.mapper.TagMapper;
import forum.service.security.UserRightsChecker;
import forum.utils.SearchFiltersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PostService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final TagService tagService;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostMapper postMapper,
                       PostRepository postRepository,
                       TagService tagService,
                       UserRepository userRepository
    ) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.userRepository = userRepository;
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

        return postMapper.toDto(post);
    }

    public List<PostDTO> getPosts(String query,
                                  String sortBy,
                                  String order,
                                  int page,
                                  int pageSize
    ){
        log.debug("Fetching: posts page {} pageSize {}", page, pageSize);

        List<Post> posts = postRepository.findAllByContentContainingOrTitleContaining(query, query,
                SearchFiltersUtil.getPageRequest(sortBy, order, page, pageSize,
                        Arrays.asList("createdAt", "title", "content")
                )
        );

        return postMapper.toDto(posts);
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
        List<Post> posts = postRepository.findAllByUser_Id(id,
                SearchFiltersUtil.getPageRequest(sortBy, order, page, pageSize,
                        Arrays.asList("createdAt", "title", "content")
                )
        );

        return postMapper.toDto(posts);
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

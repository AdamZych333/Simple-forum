package forum.service;

import forum.entity.Post;
import forum.repository.PostRepository;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import forum.service.dto.UserDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
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
import java.util.List;

@Service
@Transactional
public class PostService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRightsChecker userRightsChecker;
    private final TagMapper tagMapper;

    @Autowired
    public PostService(UserRightsChecker userRightsChecker,
                       PostMapper postMapper,
                       PostRepository postRepository,
                       TagMapper tagMapper
    ) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.userRightsChecker = userRightsChecker;
        this.tagMapper = tagMapper;
    }

    public void save(CreatedPostDTO createdPostDTO){
        log.debug("Saving post : {}", createdPostDTO);

        Post post = postMapper.toPostFromCreatedPostDTO(createdPostDTO);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        postRepository.save(post);
    }

    public PostDTO getPost(Long id){
        log.debug("Fetching post: {}", id);

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
        log.debug("Fetching posts: page {} pageSize {}", page, pageSize);

        List<Post> posts = postRepository.findAllByContentContainingOrTitleContaining(query, query,
                SearchFiltersUtil.getPageRequest(sortBy, order, page, pageSize,
                        Arrays.asList("createdAt", "title", "content")
                )
        );

        return postMapper.toDto(posts);
    }

    public List<PostDTO> getUsersPosts(Long id,
                                       String query,
                                       String sortBy,
                                       String order,
                                       int page,
                                       int pageSize
    ){
        log.debug("Fetching posts of user {}: page {} pageSize {}", id, page, pageSize);

        List<Post> posts = postRepository.findAllByUser_Id(id,
                SearchFiltersUtil.getPageRequest(sortBy, order, page, pageSize,
                        Arrays.asList("createdAt", "title", "content")
                )
        );

        return postMapper.toDto(posts);
    }

    public void deletePost(Long id, UserDTO authenticatedUser){
        log.debug("Deleting post: {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with requested id doesn't exist"));
        if(userRightsChecker.hasRights(authenticatedUser, post.getUser().getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to delete this post.");
        }

        postRepository.delete(post);
    }

    public void updatePost(CreatedPostDTO createdPostDTO, Long id, UserDTO authenticatedUser){
        log.debug("Updating post: {} to {}", id, createdPostDTO);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with requested id doesn't exist"));
        if(userRightsChecker.hasRights(authenticatedUser, post.getUser().getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to update this post.");
        }

        post.setContent(createdPostDTO.getContent());
        post.setTitle(createdPostDTO.getTitle());
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setTags(tagMapper.toEntitySetFromDTOList(createdPostDTO.getTags()));

        postRepository.save(post);
    }


}

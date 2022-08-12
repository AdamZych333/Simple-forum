package forum.service;

import forum.config.Constants;
import forum.entity.Post;
import forum.entity.User;
import forum.repository.PostRepository;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import forum.service.dto.UserDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.mapper.PostMapper;
import forum.service.security.UserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRightsChecker userRightsChecker;

    @Autowired
    public PostService(UserRightsChecker userRightsChecker, PostMapper postMapper, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.userRightsChecker = userRightsChecker;
    }

    public void save(CreatedPostDTO createdPostDTO){
        log.debug("Request to save post : {}", createdPostDTO);

        Post post = postMapper.toPostFromCreatedPostDTO(createdPostDTO);

        postRepository.save(post);
    }

    public PostDTO getPost(Long id){
        log.debug("Request to get post");

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
        log.debug("Request to get posts");

        List<String> allowedList = Arrays.asList("createdAt", "title", "content");
        String sortedField = allowedList.contains(sortBy)? sortBy: allowedList.get(0);
        Sort sort = Sort.by(
                order.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC,
                sortedField
        );
        List<Post> posts = postRepository.findAllByContentContainingOrTitleContaining(query, query, PageRequest.of(page, pageSize, sort));

        return postMapper.toDto(posts);
    }

    public void deletePost(Long id, UserDTO authenticatedUser){
        log.debug("Request to delete : {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with requested id doesn't exist"));
        if(userRightsChecker.hasRights(authenticatedUser, post.getUser().getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to delete this post.");
        }

        postRepository.delete(post);
    }

    public void updatePost(CreatedPostDTO createdPostDTO, Long id, UserDTO authenticatedUser){
        log.debug("Request to update post : {}", createdPostDTO);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with requested id doesn't exist"));
        if(userRightsChecker.hasRights(authenticatedUser, post.getUser().getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to update this post.");
        }

        post.setContent(createdPostDTO.getContent());
        post.setTitle(createdPostDTO.getTitle());

        postRepository.save(post);
    }


}

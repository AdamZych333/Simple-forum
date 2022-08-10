package forum.service;

import forum.entity.Post;
import forum.repository.PostRepository;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private PostMapper postMapper;
    private PostRepository postRepository;

    @Autowired
    public PostService(PostMapper postMapper, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
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

    public List<PostDTO> getPosts(String query){
        log.debug("Request to get posts");

        List<Post> posts =postRepository.findAllByContentContainingOrTitleContaining(query, query);

        return postMapper.toDto(posts);
    }
}

package forum.service;

import forum.entity.Post;
import forum.repository.PostRepository;
import forum.service.dto.CreatedPostDTO;
import forum.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        log.debug("Request to save Post : {}", createdPostDTO);

        Post post = postMapper.toPostFromCreatedPostDTO(createdPostDTO);

        postRepository.save(post);
    }
}

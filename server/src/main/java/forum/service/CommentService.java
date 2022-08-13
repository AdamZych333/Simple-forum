package forum.service;

import forum.entity.Comment;
import forum.entity.Post;
import forum.entity.User;
import forum.repository.CommentRepository;
import forum.repository.PostRepository;
import forum.service.dto.CommentDTO;
import forum.service.dto.CreatedCommentDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class CommentService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentMapper commentMapper,
                          CommentRepository commentRepository,
                          PostRepository postRepository
    ) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void save(CreatedCommentDTO createdCommentDTO, User authenticatedUser, Long postId){
        log.debug("Saving: comment {} in post {}", createdCommentDTO, postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with requested id doesn't exist"));

        Comment comment = commentMapper.toCommentFromCreatedCommentDTO(createdCommentDTO);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        comment.setPost(post);
        comment.setUser(authenticatedUser);

        commentRepository.save(comment);
    }

    public List<CommentDTO> getPostComments(Long postId){
        log.debug("Fetching: comments of post {}", postId);

        List<Comment> comments = commentRepository.findAllByPost_Id(postId);

        return commentMapper.toDto(comments);
    }
}

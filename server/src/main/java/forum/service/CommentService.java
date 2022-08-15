package forum.service;

import forum.entity.Comment;
import forum.entity.Post;
import forum.entity.User;
import forum.repository.CommentRepository;
import forum.service.dto.CommentDTO;
import forum.service.dto.CreatedCommentDTO;
import forum.service.dto.UpdateCommentDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.mapper.CommentMapper;
import forum.service.security.UserRightsChecker;
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

    @Autowired
    public CommentService(CommentMapper commentMapper,
                          CommentRepository commentRepository
    ) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    public void save(CreatedCommentDTO createdCommentDTO, User authenticatedUser, Post post){
        log.debug("Saving: comment {} in post {}", createdCommentDTO, post);

        Comment parent = commentRepository.findByIdAndPost_Id(createdCommentDTO.getParentID(), post.getId())
                .orElse(null);

        Comment comment = commentMapper.toCommentFromCreatedCommentDTO(createdCommentDTO);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        comment.setLastModificationAt(new Timestamp(System.currentTimeMillis()));
        comment.setPost(post);
        comment.setUser(authenticatedUser);
        comment.setParent(parent);

        commentRepository.save(comment);
    }

    public List<CommentDTO> getPostComments(Long postId){
        log.debug("Fetching: comments of post {}", postId);

        List<Comment> comments = commentRepository.findAllByPost_IdAndParent_Id(postId, null);

        return commentMapper.toDto(comments);
    }

    public CommentDTO getPostComment(Long id, Long postID){
        log.debug("Fetching: comment {} of post {}", id, postID);

        Comment comment = commentRepository.findByIdAndPost_Id(id, postID)
                .orElseThrow(() -> new EntityNotFoundException("Comment with requested id doesn't exist under requested post"));

        return commentMapper.toDto(comment);
    }

    public void updateComment(Long id, Long postID, UpdateCommentDTO updateCommentDTO, User authenticatedUser){
        log.debug("Updating: comment {} to {}", id, updateCommentDTO);

        Comment comment = commentRepository.findByIdAndPost_Id(id, postID)
                .orElseThrow(() -> new EntityNotFoundException("Comment with requested id doesn't exist under requested post"));

        if(!UserRightsChecker.hasRights(authenticatedUser, comment.getUser().getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to update this comment.");
        }

        comment.setContent(updateCommentDTO.getContent());
        comment.setLastModificationAt(new Timestamp(System.currentTimeMillis()));

        commentRepository.save(comment);
    }

    public void deleteComment(Long id, Long postID, User authenticatedUser){
        log.debug("Deleting: comment {}", id);

        Comment comment = commentRepository.findByIdAndPost_Id(id, postID)
                .orElseThrow(() -> new EntityNotFoundException("Comment with requested id doesn't exist under requested post"));
        if(!UserRightsChecker.hasRights(authenticatedUser, comment.getUser().getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to update this comment.");
        }

        commentRepository.delete(comment);
    }
}

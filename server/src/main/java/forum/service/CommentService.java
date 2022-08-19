package forum.service;

import forum.entity.Comment;
import forum.entity.Post;
import forum.entity.User;
import forum.repository.CommentRepository;
import forum.repository.PostRepository;
import forum.repository.UserRepository;
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
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentMapper commentMapper,
                          CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository
    ) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void save(CreatedCommentDTO createdCommentDTO, User authenticatedUser, Long postID){
        log.debug("Saving: comment {} in post {}", createdCommentDTO, postID);

        Post post = postRepository.findById(postID)
                .orElseThrow(() -> new EntityNotFoundException("Post with requested id doesn't exists."));
        Comment parent = commentRepository.findByIdAndPost_Id(createdCommentDTO.getParentID(), postID)
                .orElse(null);

        Comment comment = commentMapper.toCommentFromCreatedCommentDTO(createdCommentDTO);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        comment.setLastModificationAt(new Timestamp(System.currentTimeMillis()));
        comment.setPost(post);
        comment.setUser(authenticatedUser);
        comment.setParent(parent);

        commentRepository.save(comment);
    }

    public List<CommentDTO> getPostComments(Long postID){
        log.debug("Fetching: comments of post {}", postID);

        handleIfPostExists(postID);
        List<Comment> comments = commentRepository.findAllByPost_IdAndParent_Id(postID, null);

        return commentMapper.toDto(comments);
    }

    public CommentDTO getPostComment(Long id, Long postID){
        log.debug("Fetching: comment {} of post {}", id, postID);

        handleIfPostExists(postID);
        Comment comment = commentRepository.findByIdAndPost_Id(id, postID)
                .orElseThrow(() -> new EntityNotFoundException("Comment with requested id doesn't exist under requested post"));

        return commentMapper.toDto(comment);
    }

    public List<CommentDTO> getUserComments(Long userID){
        log.debug("Fetching: comments of user {}", userID);

        if(!userRepository.existsById(userID)){
            throw new EntityNotFoundException("User with requested id doesn't exist.");
        }
        List<Comment> comments = commentRepository.findAllByUser_Id(userID);

        return commentMapper.toDto(comments);
    }

    public void updateComment(Long id, Long postID, UpdateCommentDTO updateCommentDTO, User authenticatedUser){
        log.debug("Updating: comment {} to {}", id, updateCommentDTO);

        Comment comment = getEditableComment(id, postID, authenticatedUser);
        comment.setContent(updateCommentDTO.getContent());
        comment.setLastModificationAt(new Timestamp(System.currentTimeMillis()));

        commentRepository.save(comment);
    }

    public void deleteComment(Long id, Long postID, User authenticatedUser){
        log.debug("Deleting: comment {}", id);

        Comment comment = getEditableComment(id, postID, authenticatedUser);

        commentRepository.delete(comment);
    }

    private Comment getEditableComment(Long id, Long postID, User authenticatedUser){
        handleIfPostExists(postID);
        Comment comment = commentRepository.findByIdAndPost_Id(id, postID)
                .orElseThrow(() -> new EntityNotFoundException("Comment with requested id doesn't exist under requested post"));
        if(!UserRightsChecker.hasRights(authenticatedUser, comment.getUser().getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to update this comment.");
        }

        return comment;
    }

    private void handleIfPostExists(Long postID){
        if(!postRepository.existsById(postID)){
            throw new EntityNotFoundException("Post with requested id doesn't exists.");
        }
    }
}

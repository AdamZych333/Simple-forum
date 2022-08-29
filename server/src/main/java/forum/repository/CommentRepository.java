package forum.repository;

import forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_IdAndParent_IdOrderByCreatedAtDesc(Long id, Long parentID);

    Optional<Comment> findByIdAndPost_IdOrderByCreatedAtDesc(Long id, Long postID);

    List<Comment> findAllByUser_IdOrderByCreatedAtDesc(Long userID);

    @Query("select c from Comment c where c.content like %:content% and " +
            "(:userID is null or c.user.id = :userID) and" +
            "(:postID is null or c.post.id = :postID)")
    List<Comment> searchComments(String content, Long userID, Long postID);
}

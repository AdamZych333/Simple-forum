package forum.repository;

import forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_IdAndParent_Id(Long id, Long parentID);

    Optional<Comment> findByIdAndPost_Id(Long id, Long postID);

    List<Comment> findAllByUser_Id(Long userID);
}

package forum.repository;

import forum.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByPost_IdAndUser_Id(Long postID, Long userID);

    List<Follow> findAllByUser_Id(Long userID);

    boolean existsByUser_IdAndPost_Id(Long userID, Long postID);
}

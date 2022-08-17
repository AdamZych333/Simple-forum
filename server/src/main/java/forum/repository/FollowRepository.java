package forum.repository;

import forum.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByPost_IdAndUser_Id(Long postID, Long userID);
}

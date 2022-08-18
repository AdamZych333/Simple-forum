package forum.repository;

import forum.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUser_IdAndPost_Id(Long userID, Long postID);

    boolean existsByUser_IdAndPost_Id(Long userID, Long postID);

    Set<Vote> findAllByPost_IdAndType(Long postID, String type);
}

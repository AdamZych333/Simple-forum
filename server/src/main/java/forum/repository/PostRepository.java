package forum.repository;

import forum.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findAllByContentContainingOrTitleContaining(String content, String title, PageRequest pageRequest);

    List<Post> findAllByContentContainingOrTitleContaining(String content, String title, Sort sort);

    List<Post> findAllByUser_Id(Long userId, PageRequest pageRequest);

    List<Post> findAllByUser_Id(Long userId, Sort sort);

    @Query("SELECT p FROM Post p JOIN p.follows f WHERE f.user.id = ?1")
    List<Post> findAllFollowedByUser(Long userID, PageRequest pageRequest);
}

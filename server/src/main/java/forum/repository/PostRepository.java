package forum.repository;

import forum.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findAllByContentContainingOrTitleContaining(String content, String title, Sort sort);
}

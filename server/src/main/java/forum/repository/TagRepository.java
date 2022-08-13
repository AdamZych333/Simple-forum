package forum.repository;

import forum.entity.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    @Query("select t from Tag t left join t.posts p group by t.id order by count(t) desc")
    List<Tag> findAllByPostsCount(PageRequest pageRequest);
}

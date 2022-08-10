package forum.repository;

import forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String email);

    User findByEmail(String email);
}

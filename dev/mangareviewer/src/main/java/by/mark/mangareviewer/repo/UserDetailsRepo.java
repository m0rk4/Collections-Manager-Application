package by.mark.mangareviewer.repo;

import by.mark.mangareviewer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
    User findByName(String name);
    User findByEmail(String email);
}

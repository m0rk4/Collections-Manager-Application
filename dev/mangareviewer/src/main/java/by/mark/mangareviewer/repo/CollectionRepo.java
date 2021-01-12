package by.mark.mangareviewer.repo;

import by.mark.mangareviewer.domain.Collection;
import by.mark.mangareviewer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepo extends JpaRepository<Collection, Long> {
    List<Collection> findAllByUser(User user);
}

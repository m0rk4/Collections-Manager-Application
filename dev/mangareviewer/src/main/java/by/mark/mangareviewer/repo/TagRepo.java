package by.mark.mangareviewer.repo;

import by.mark.mangareviewer.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<Tag, Long> {
}

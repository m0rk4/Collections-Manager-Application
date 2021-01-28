package by.mark.mangareviewer.repo;

import by.mark.mangareviewer.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepo extends JpaRepository<Theme, Long> {
}

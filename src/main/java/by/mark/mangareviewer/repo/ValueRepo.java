package by.mark.mangareviewer.repo;

import by.mark.mangareviewer.domain.Value;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueRepo  extends JpaRepository<Value, Long> {
}

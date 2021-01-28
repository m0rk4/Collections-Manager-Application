package by.mark.mangareviewer.repo;

import by.mark.mangareviewer.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);
}

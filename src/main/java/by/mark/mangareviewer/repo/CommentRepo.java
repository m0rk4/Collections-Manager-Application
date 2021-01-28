package by.mark.mangareviewer.repo;

import by.mark.mangareviewer.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}

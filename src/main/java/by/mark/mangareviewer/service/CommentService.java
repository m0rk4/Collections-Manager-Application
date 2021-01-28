package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.Comment;
import by.mark.mangareviewer.domain.user.User;

public interface CommentService {
    Comment createComment(Comment comment, User author);
}

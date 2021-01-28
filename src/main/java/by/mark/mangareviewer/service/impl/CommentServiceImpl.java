package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.Comment;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.dto.EventType;
import by.mark.mangareviewer.dto.ObjectType;
import by.mark.mangareviewer.repo.CommentRepo;
import by.mark.mangareviewer.service.CommentService;
import by.mark.mangareviewer.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    @Override
    public Comment createComment(Comment comment, User author) {
        comment.setAuthor(author);
        Comment commentFromDb = commentRepo.save(comment);
        wsSender.accept(EventType.CREATE, commentFromDb);
        return commentFromDb;
    }
}

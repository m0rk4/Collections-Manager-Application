package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Comment;
import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.AuthService;
import by.mark.mangareviewer.service.CommentService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comment")
@PreAuthorize("hasAuthority('USER')")
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;

    @Autowired
    public CommentController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment createComment(
            @RequestBody Comment comment,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal User basicUser
    ) {
        User currentUser = authService.getCurrentUser(basicUser, oAuth2User);
        return commentService.createComment(comment, currentUser);
    }
}

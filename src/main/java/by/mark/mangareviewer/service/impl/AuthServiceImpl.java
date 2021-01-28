package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.AuthService;
import by.mark.mangareviewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser(
            @AuthenticationPrincipal User basicUser,
            @AuthenticationPrincipal OAuth2User oAuth2User
    ) {
        String id = basicUser != null ? basicUser.getId() :
                oAuth2User != null ? oAuth2User.getName() : null;
        if (id != null) {
            return userService.findById(id).orElse(null);
        }
        return null;
    }

}

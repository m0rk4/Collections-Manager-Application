package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.user.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {
    User getCurrentUser(User basicUser, OAuth2User oAuth2User);
}

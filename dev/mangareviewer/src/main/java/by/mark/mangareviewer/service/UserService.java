package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {
    User updateUser(Map<String, Object> attributes);
}

package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Set;

public interface UserService extends UserDetailsService {
    User updateOAuth2User(Map<String, Object> attributes, Set<GrantedAuthority> authorities);
    boolean addNewUser(User user);
}

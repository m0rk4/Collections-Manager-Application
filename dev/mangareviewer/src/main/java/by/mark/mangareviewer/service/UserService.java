package by.mark.mangareviewer.service;

import by.mark.mangareviewer.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {
    User updateOAuth2User(Map<String, Object> attributes, Set<GrantedAuthority> authorities);
    boolean addNewUser(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    List<User> updateAdmins(List<User> users, boolean isForceMakeAdmin);
    void deleteUsers(List<User> users);
    void updateUsersLocked(List<User> users, boolean isNonLocked);
}

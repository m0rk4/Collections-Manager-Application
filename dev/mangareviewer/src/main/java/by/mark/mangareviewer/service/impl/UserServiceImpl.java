package by.mark.mangareviewer.service.impl;

import by.mark.mangareviewer.domain.user.Role;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.repo.UserDetailsRepo;
import by.mark.mangareviewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDetailsRepo userDetailsRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDetailsRepo userDetailsRepo, PasswordEncoder passwordEncoder) {
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepo.findByName(username);
        if (user == null) {
            user = userDetailsRepo.findByEmail(username);
            if (user == null)
                throw new UsernameNotFoundException("User with such login or email do not exists");
        }
        if (user.getPassword() == null)
            throw new UsernameNotFoundException("User with such login or email do not exists");
        return user;
    }

    @Override
    public User updateOAuth2User(Map<String, Object> attributes, Set<GrantedAuthority> authorities) {
        String id = attributes.get("id") instanceof String ?
                (String) attributes.get("id") : String.valueOf(attributes.get("id"));
        User user = userDetailsRepo.findById(id).orElseGet(() -> createOAuth2User(id, attributes));
        authorities.addAll(user.getAuthorities());
        user.setLastVisit(LocalDateTime.now());
        return userDetailsRepo.save(user);
    }

    @Override
    public boolean addNewUser(User user) {
        User userFromDbLogin = userDetailsRepo.findByName(user.getName());
        if (userFromDbLogin != null)
            return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));
        user.setNonLocked(true);
        user.setLastVisit(LocalDateTime.now());
        userDetailsRepo.save(user);
        return true;
    }

    private User createOAuth2User(String id, Map<String, Object> attributes) {
        User user = new User();
        user.setId(id);
        user.setEmail((String) attributes.get("email"));
        user.setName((String) Optional.ofNullable(attributes.get("name")).orElse(attributes.get("login")));
        user.setUserpic((String) attributes.get("avatar_url"));
        user.setRoles(Collections.singleton(Role.USER));
        user.setNonLocked(true);
        return user;
    }
}

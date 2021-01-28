package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @JsonView(Views.IdText.class)
    public User getUser(@PathVariable("id") User userFromDb) {
        return userFromDb;
    }

    @GetMapping
    @JsonView(Views.FullProfile.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> usersList() {
        return userService.findAll();
    }

    @PostMapping("deleteUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.FullProfile.class)
    public List<User> deleteUsers(@RequestBody List<User> users) {
        userService.deleteUsers(users);
        return userService.findAll();
    }

    @PostMapping("makeAdmins")
    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.FullProfile.class)
    public List<User> makeUsersAdmins(@RequestBody List<User> users) {
        userService.updateAdmins(users, true);
        return userService.findAll();
    }

    @PostMapping("unmakeAdmins")
    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.FullProfile.class)
    public List<User> unmakeUsersAdmins(@RequestBody List<User> users) {
        userService.updateAdmins(users, false);
        return userService.findAll();
    }

    @PostMapping("blockUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.FullProfile.class)
    public List<User> blockUsers(@RequestBody List<User> users) {
        userService.updateUsersLocked(users, false);
        return userService.findAll();
    }

    @PostMapping("unlockUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(Views.FullProfile.class)
    public List<User> unlockUsers(@RequestBody List<User> users) {
        userService.updateUsersLocked(users, true);
        return userService.findAll();
    }
}

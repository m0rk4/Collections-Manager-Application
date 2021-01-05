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
@PreAuthorize("hasAuthority('ADMIN')")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(Views.FullProfile.class)
    public List<User> usersList() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User getUser(@PathVariable("id") User userFromDb) {
        return userFromDb;
    }

    @PostMapping("deleteUsers")
    @JsonView(Views.FullProfile.class)
    public List<User> deleteUsers(@RequestBody List<User> users) {
        userService.deleteUsers(users);
        return userService.findAll();
    }

    @PostMapping("makeAdmins")
    @JsonView(Views.FullProfile.class)
    public List<User> makeUsersAdmins(@RequestBody List<User> users) {
        userService.updateAdmins(users, true);
        return userService.findAll();
    }

    @PostMapping("unmakeAdmins")
    @JsonView(Views.FullProfile.class)
    public List<User> unmakeUsersAdmins(@RequestBody List<User> users) {
        userService.updateAdmins(users, false);
        return userService.findAll();
    }

    @PostMapping("blockUsers")
    @JsonView(Views.FullProfile.class)
    public List<User> blockUsers(@RequestBody List<User> users) {
        userService.updateUsersLocked(users, false);
        return userService.findAll();
    }

    @PostMapping("unlockUsers")
    @JsonView(Views.FullProfile.class)
    public List<User> unlockUsers(@RequestBody List<User> users) {
        userService.updateUsersLocked(users, true);
        return userService.findAll();
    }
}

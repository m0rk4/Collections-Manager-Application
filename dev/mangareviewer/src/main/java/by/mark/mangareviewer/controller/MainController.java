package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final AuthService authService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    public MainController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String main(
            Model model,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal User basicUser
    ) {
        User currentUser = authService.getCurrentUser(basicUser, oAuth2User);
        model.addAttribute("profile", currentUser);

        model.addAttribute("isDevMode", "dev".equals(activeProfile));
        return "index";
    }
}

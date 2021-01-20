package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.domain.Views;
import by.mark.mangareviewer.domain.user.User;
import by.mark.mangareviewer.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    private final AuthService authService;

    @Value("${spring.profiles.active}")
    private String activeProfile;
    private final ObjectWriter profileWriter;

    @Autowired
    public MainController(AuthService authService, ObjectMapper objectMapper) {
        this.authService = authService;
        objectMapper.setConfig(objectMapper.getSerializationConfig());
        this.profileWriter = objectMapper.writerWithView(Views.IdName.class);
    }

    @GetMapping
    public String main(
            Model model,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal User basicUser
    ) throws JsonProcessingException {
        User currentUser = authService.getCurrentUser(basicUser, oAuth2User);
        String serializedUser = profileWriter.writeValueAsString(currentUser);
        log.debug(serializedUser);
        model.addAttribute("profile", serializedUser);
        log.info("IN MAIN CONTROLLER: {}", currentUser);
        model.addAttribute("isDevMode", "dev".equals(activeProfile));
        return "index";
    }

}

package by.mark.mangareviewer.controller;


import by.mark.mangareviewer.dto.LoginFormDto;
import by.mark.mangareviewer.dto.RegistrationFormDto;
import by.mark.mangareviewer.service.UserService;
import by.mark.mangareviewer.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class AuthController {

    private final UserService userService;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public AuthController(UserService userService, AuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid RegistrationFormDto userDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(
                    ControllerUtils.getValidationErrors(bindingResult),
                    HttpStatus.OK);

        return userService.addNewUser(userDto.toUser())
                ?
                new ResponseEntity<>(
                        HttpStatus.OK)
                :
                new ResponseEntity<>(
                        ControllerUtils.getDefaultAuthErrorMessage(),
                        HttpStatus.OK);
    }

    @PostMapping("signin")
    public ResponseEntity<?> login(@RequestBody LoginFormDto loginFormDto, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginFormDto.getUsername(), loginFormDto.getPassword());
        token.setDetails(new WebAuthenticationDetails(request));
        try {
            Authentication auth = authenticationProvider.authenticate(token);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>(ControllerUtils.getMessageError(exception.getMessage()), HttpStatus.OK);
        }
    }

}

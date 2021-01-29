package by.mark.mangareviewer.security;

import by.mark.mangareviewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyCustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyCustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        super();
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authName = authentication.getName();
        UserDetails userDetails = userService.loadUserByUsername(authName);
        String password = userDetails.getPassword();

        String authenticationCredentials = (String) authentication.getCredentials();
        if (passwordEncoder.matches(authenticationCredentials, password)) {
            if (userDetails.isAccountNonLocked()) {
                return getUsernamePasswordAuthenticationToken(authentication, userDetails);
            } else {
                throw new BadCredentialsException("Your account is locked by Admin");
            }
        } else {
            throw new BadCredentialsException("Incorrect Password");
        }
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(
            Authentication authentication,
            UserDetails userDetails
    ) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(authentication.getDetails());
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AuthenticationProvider.class);
    }
}

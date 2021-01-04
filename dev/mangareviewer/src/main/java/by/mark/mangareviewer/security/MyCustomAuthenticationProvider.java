package by.mark.mangareviewer.security;

import by.mark.mangareviewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyCustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Autowired
    public MyCustomAuthenticationProvider(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
        if (userDetails != null) {
            if (authentication.getCredentials().equals(userDetails.getPassword())) {
                return getUsernamePasswordAuthenticationToken(authentication, userDetails);
            } else {
                throw new BadCredentialsException("Incorrect Password");
            }
        } else {
            throw new UsernameNotFoundException("User with such login or email do not exists");
        }
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(
            Authentication authentication, UserDetails userDetails
    ) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
        usernamePasswordAuthenticationToken.setDetails(authentication.getDetails());
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}

package by.mark.mangareviewer.controller;

import by.mark.mangareviewer.dto.UserDto;
import by.mark.mangareviewer.service.UserService;
import by.mark.mangareviewer.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity registerUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(
                    ControllerUtils.getValidationErrors(bindingResult),
                    HttpStatus.OK);

        return userService.addNewUser(userDto.toUser()) ?
                new ResponseEntity(
                        HttpStatus.OK)
                :
                new ResponseEntity(
                        ControllerUtils.getDefaultAuthErrorMessage(),
                        HttpStatus.OK);
    }

}

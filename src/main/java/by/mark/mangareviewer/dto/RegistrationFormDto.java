package by.mark.mangareviewer.dto;

import by.mark.mangareviewer.domain.user.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationFormDto {
    @NotBlank(message = "Empty Email")
    @Email(message = "Incorrect Email")
    private String email;

    @NotBlank(message = "Empty Name")
    @Size(min = 3, max = 15, message = "Incorrect Name Size")
    private String name;

    @NotBlank(message = "Empty Password")
    @Size(min = 5, max = 15, message = "Incorrect Password Size")
    private String password;

    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        return user;
    }
}

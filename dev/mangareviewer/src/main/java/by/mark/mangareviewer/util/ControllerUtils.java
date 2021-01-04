package by.mark.mangareviewer.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ControllerUtils {

    private static final String DUP_MESSAGE = "User with such Login exists";

    public static Map<Object, Object> getValidationErrors(BindingResult bindingResult) {
        Map<Object, Object> response = new HashMap<>();
        response.put("message", bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((m1, m2) -> m1 + "\n" + m2)
                .orElse("Check correctness"));
        return response;
    }

    public static Map<Object,Object> getDefaultAuthErrorMessage() {
        return getMessage(DUP_MESSAGE);
    }

    public static Map<Object,Object> getMessageError(String message) {
       return getMessage(message);
    }

    private static Map<Object,Object> getMessage(String message) {
        Map<Object, Object> response = new HashMap<>();
        response.put("message", message);
        return response;
    }


}

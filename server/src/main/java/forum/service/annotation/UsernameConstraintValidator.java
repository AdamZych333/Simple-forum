package forum.service.annotation;

import forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintValidator implements ConstraintValidator<ValidName, String> {
    private final UserService userService;

    @Autowired
    public UsernameConstraintValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.length() > 5 && !userService.existsByName(name);
    }
}

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
        if(name.length() < 5){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Username should be at least 5 characters long.").addConstraintViolation();
            return false;
        }
        if(userService.existsByName(name)){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("User with that name already exists.").addConstraintViolation();
            return false;
        }

        return true;
    }
}

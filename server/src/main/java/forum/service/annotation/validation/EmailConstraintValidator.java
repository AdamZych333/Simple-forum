package forum.service.annotation.validation;

import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return EmailValidator.getInstance().isValid(email);
    }
}

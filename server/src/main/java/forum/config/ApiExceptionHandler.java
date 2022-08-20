package forum.config;

import forum.controller.UserController;
import forum.service.exception.EntityAlreadyExistsException;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler({
            EntityAlreadyExistsException.class,
            EntityNotFoundException.class,
            ForbiddenException.class
    })
    public ResponseEntity<Object> handleCustomException(HttpServletRequest req, Exception ex){
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        HttpStatus status;
        if(ex instanceof EntityAlreadyExistsException){
            status = HttpStatus.BAD_REQUEST;
        }else if(ex instanceof EntityNotFoundException){
            status = HttpStatus.NOT_FOUND;
        }else{
            status = HttpStatus.FORBIDDEN;
        }
        String body = ex.getMessage();

        return new ResponseEntity<>(
                Collections.singletonMap("message", body),
                status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex){
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        HashMap<String, List<String>> body = new HashMap<>();
        for(FieldError error : ex.getFieldErrors()){
            List<String> messages = Collections.emptyList();
            if(error.getDefaultMessage() != null) {
                messages = Arrays.asList(error.getDefaultMessage().split(","));
            }
            body.put(error.getField(), messages);
        }

        return new ResponseEntity<>(
                Collections.singletonMap("message", body),
                status);
    }
}

package forum.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostAlreadyFollowedException extends RuntimeException {
    public PostAlreadyFollowedException(String message) {
        super(message);
    }
}

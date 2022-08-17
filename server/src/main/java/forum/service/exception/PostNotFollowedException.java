package forum.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostNotFollowedException extends RuntimeException {
    public PostNotFollowedException(String message) {
        super(message);
    }
}
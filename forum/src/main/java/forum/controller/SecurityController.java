package forum.controller;

import forum.service.UserService;
import forum.service.dto.RegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping(path="/api/auth")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<Void> register(
            @RequestBody @Validated RegisterDTO registerDTO
    ) throws URISyntaxException {
        log.debug("Request to save user : {}", registerDTO);

        userService.save(registerDTO);

        return ResponseEntity.created(new URI("")).build();
    }
}

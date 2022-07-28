package forum.controller;

import forum.repository.UserRepository;
import forum.service.UserService;
import forum.service.dto.RegisterDTO;
import forum.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(path="/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> userDTOS = userService.getUsers();

        return ResponseEntity.ok(userDTOS);
    }

    @PostMapping
    public ResponseEntity<Void> addUser(
            @RequestBody @Validated RegisterDTO registerDTO
    ) throws URISyntaxException {
        log.debug("REST request to save User : {}", registerDTO);

        userService.save(registerDTO);

        return ResponseEntity.created(new URI("")).build();
    }
}

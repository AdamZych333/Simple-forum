package forum.controller;

import forum.service.UserService;
import forum.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        log.debug("Request to get users.");

        List<UserDTO> userDTOS = userService.getUsers();

        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(
            @PathVariable final Long id
    ){
        log.debug("Request to get user: {}", id);

        UserDTO userDTO = userService.getUserById(id);

        return ResponseEntity.ok(userDTO);
    }
}

package forum.controller;

import forum.config.security.JwtTokenProvider;
import forum.service.UserService;
import forum.service.dto.LoginDTO;
import forum.service.dto.RegisterDTO;
import forum.service.dto.TokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(
            @RequestBody @Validated LoginDTO loginDTO
    ) {
        log.debug("Request to create new token: {}", loginDTO);

        try {
            String email = loginDTO.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginDTO.getPassword()));

            String token = jwtTokenProvider.createToken();
            this.userService.setToken(email, token);

            TokenDTO response = new TokenDTO();
            response.setToken(token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid login/password supplied");
        }
    }
}

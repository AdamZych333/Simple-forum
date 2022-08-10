package forum.controller;

import forum.config.security.JwtTokenProvider;
import forum.service.CustomUserDetailsService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping(path="/api/auth")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

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

            String token = jwtTokenProvider.createToken(email);

            return ResponseEntity.ok(new TokenDTO(token));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid login/password supplied");
        }
    }
}

package forum.service;

import forum.entity.User;
import forum.repository.UserRepository;
import forum.service.exception.TokenNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomUserDetailsService implements AppUserDetailsService{

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) {
        User user = this.userRepository.findByEmail(login);
        if(user == null) throw new UsernameNotFoundException(login);


        return user;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByToken(String token) {
        User user = this.userRepository.findByToken(token);
        if(user == null) throw new TokenNotFoundException(token);

        return user;
    }
}

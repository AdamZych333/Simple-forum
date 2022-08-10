package forum.service;

import forum.entity.Role;
import forum.entity.User;
import forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class CustomUserDetailsService implements AppUserDetailsService{

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) {
        User user = this.userRepository.findByEmail(login);
        if(user == null) throw new UsernameNotFoundException(login);

        return getUserWithAuthorities(user);
    }

    private User getUserWithAuthorities(User user){
        Set<Role> roles = user.getRoles();
        roles.forEach(role -> user.addAuthority(role.getName()));
        return user;
    }
}

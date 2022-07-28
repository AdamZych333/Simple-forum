package forum.service;

import forum.entity.User;
import forum.repository.UserRepository;
import forum.service.dto.RegisterDTO;
import forum.service.dto.UserDTO;
import forum.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void save(RegisterDTO registerDTO){
        log.debug("Request to save user : {}", registerDTO);

        User user = userMapper.toEntityFromRegisterDTO(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public List<UserDTO> getUsers(){
        log.debug("Request to get all users");

        List<User> users = userRepository.findAll();

        return userMapper.toDto(users);
    }

    public void setToken(String email, String token){
        User user = userRepository.findByEmail(email);

        user.setToken(token);

        userRepository.save(user);
    }
}

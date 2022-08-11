package forum.service;

import forum.config.Constants;
import forum.entity.Role;
import forum.entity.User;
import forum.repository.RoleRepository;
import forum.repository.UserRepository;
import forum.service.dto.RegisterDTO;
import forum.service.dto.UpdateUserDTO;
import forum.service.dto.UserDTO;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.exception.UserAlreadyExistsException;
import forum.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       RoleRepository roleRepository
    ){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void save(RegisterDTO registerDTO){
        log.debug("Request to save user : {}", registerDTO);

        User user = userRepository.findByEmail(registerDTO.getEmail());
        if(user != null){
            throw new UserAlreadyExistsException("User with provided email already exists");
        }
        user = userMapper.toUserFromRegisterDTO(registerDTO);
        Role userRole = roleRepository.findByName(Constants.Role.USER.name);
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public List<UserDTO> getUsers(){
        log.debug("Request to get all users");

        List<User> users = userRepository.findAll();

        return userMapper.toDto(users);
    }

    public UserDTO getUserByEmail(String email){
        log.debug("Request to get user : {}", email);

        User user = this.userRepository.findByEmail(email);

        return this.userMapper.toDto(user);
    }

    public UserDTO getUserById(Long id){
        log.debug("Request to get user : {}", id);

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with requested id doesn't exist"));

        return this.userMapper.toDto(user);
    }

    public void updateUser(UpdateUserDTO newUserDTO, Long id, UserDTO authenticatedUser){
        log.debug("Request to update user {} by {}", id, authenticatedUser);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with requested id doesn't exist"));

        if(!user.getId().equals(authenticatedUser.getId()) &&
                user.getRoles().stream()
                        .noneMatch(a -> a.getName().equals(Constants.Role.ADMIN.name))
        ){
            throw new ForbiddenException("Requesting user is neither author of the post nor administrator");
        }

        user.setName(newUserDTO.getName());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        userRepository.save(user);
    }
}

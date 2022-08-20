package forum.service;

import forum.config.Constants;
import forum.entity.Role;
import forum.entity.User;
import forum.repository.RoleRepository;
import forum.repository.UserRepository;
import forum.service.dto.RegisterDTO;
import forum.service.dto.UpdateUserDTO;
import forum.service.dto.UserDTO;
import forum.service.exception.EntityAlreadyExistsException;
import forum.service.exception.EntityNotFoundException;
import forum.service.exception.ForbiddenException;
import forum.service.mapper.UserMapper;
import forum.service.security.UserRightsChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        log.debug("Saving user : {}", registerDTO);

        User user = userMapper.toUserFromRegisterDTO(registerDTO);
        Role userRole = roleRepository.findByName(Constants.Role.USER.name);
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public List<UserDTO> getUsers(){
        log.debug("Fetching all users");

        List<User> users = userRepository.findAll();

        return userMapper.toDto(users);
    }

    public boolean existsByName(String name){
        log.debug("Checking if exists user by name: {}", name);

        return userRepository.existsByName(name);
    }

    public UserDTO getUserByEmail(String email){
        log.debug("Fetching user by email: {}", email);

        User user = this.userRepository.findByEmail(email);

        return this.userMapper.toDto(user);
    }

    public UserDTO getUserById(Long id){
        log.debug("Fetching user by id: {}", id);

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with requested id doesn't exist."));

        return this.userMapper.toDto(user);
    }

    public void updateUser(Long id, UpdateUserDTO newUserDTO, User authenticatedUser){
        log.debug("Updating user {} to {}", id, newUserDTO);

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with requested id doesn't exist."));
        if(!UserRightsChecker.hasRights(authenticatedUser, user.getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to update this user.");
        }

        user.setName(newUserDTO.getName());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        userRepository.save(user);
    }

    public void deleteUser(Long id, User authenticatedUser){
        log.debug("Deleting user {}", id);

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with requested id doesn't exist."));
        if(!UserRightsChecker.hasRights(authenticatedUser, user.getId())){
            throw new ForbiddenException("Requesting user doesn't have rights to delete this user.");
        }

        userRepository.delete(user);
    }

    public void makeAdmin(Long id){
        log.debug("Making admin user {}", id);

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with requested id doesn't exist."));
        Role adminRole = roleRepository.findByName(Constants.Role.ADMIN.name);
        if(adminRole != null){
            if(user.getRoles().stream().anyMatch(r -> r.getName().equals(adminRole.getName()))){
                throw new EntityAlreadyExistsException("User already is admin.");
            }
            user.getRoles().add(adminRole);
        }
        userRepository.save(user);
    }

    public void removeAdmin(Long id){
        log.debug("Removing admin user {}", id);

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with requested id doesn't exist."));
        Role adminRole = roleRepository.findByName(Constants.Role.ADMIN.name);
        if(adminRole != null){
            user.getRoles().removeIf(r -> r.getName().equals(adminRole.getName()));
        }
        userRepository.save(user);
    }
}

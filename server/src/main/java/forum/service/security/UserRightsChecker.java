package forum.service.security;

import forum.config.Constants;
import forum.service.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserRightsChecker {

    public boolean hasRights(UserDTO authenticatedUser, Long userIdToCheck){

        return !authenticatedUser.getId().equals(userIdToCheck) &&
                authenticatedUser.getRoles().stream()
                        .noneMatch(a -> a.getName().equals(Constants.Role.ADMIN.name));
    }
}

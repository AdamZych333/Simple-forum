package forum.service.security;

import forum.config.Constants;
import forum.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRightsChecker {

    public boolean hasRights(User authenticatedUser, Long userIdToCheck){

        return !authenticatedUser.getId().equals(userIdToCheck) &&
                authenticatedUser.getRoles().stream()
                        .noneMatch(a -> a.getName().equals(Constants.Role.ADMIN.name));
    }
}

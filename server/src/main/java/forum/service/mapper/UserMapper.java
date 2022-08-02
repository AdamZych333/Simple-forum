package forum.service.mapper;

import forum.entity.User;
import forum.service.dto.RegisterDTO;
import forum.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDTO> {
    User toEntityFromRegisterDTO(RegisterDTO registerDTO);
}

package forum.service.mapper;

import forum.entity.User;
import forum.service.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
    UserDTO toDto(User dto);

    List<User> toEntity(List<UserDTO> dtoList);
    List<UserDTO> toDto(List<User> entityList);
}

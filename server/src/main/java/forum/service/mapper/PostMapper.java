package forum.service.mapper;

import forum.entity.Post;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import org.mapstruct.Mapper;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<Post, PostDTO> {

    default Post toPostFromCreatedPostDTO(CreatedPostDTO createdPostDTO){
        PostDTO dto = new PostDTO();
        dto.setContent(createdPostDTO.getContent());
        dto.setUserID(createdPostDTO.getUserId());
        dto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return this.toEntity(dto);
    }
}

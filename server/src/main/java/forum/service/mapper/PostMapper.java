package forum.service.mapper;

import forum.entity.Post;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;

import java.sql.Timestamp;

public interface PostMapper extends EntityMapper<Post, PostDTO> {

    default PostDTO toPostDTOFromCreatedPostDTO(CreatedPostDTO createdPostDTO){
        PostDTO dto = new PostDTO();
        dto.setContent(createdPostDTO.getContent());
        dto.setUserID(createdPostDTO.getUserId());
        dto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return dto;
    }
}

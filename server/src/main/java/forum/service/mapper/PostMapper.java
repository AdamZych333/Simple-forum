package forum.service.mapper;

import forum.entity.Post;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<Post, PostDTO> {

    default Post toPostFromCreatedPostDTO(CreatedPostDTO createdPostDTO){
        PostDTO dto = new PostDTO();
        dto.setTitle(createdPostDTO.getTitle());
        dto.setContent(createdPostDTO.getContent());
        dto.setUserID(createdPostDTO.getUserId());
        dto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return this.toEntity(dto);
    }

    @Mapping(source = "userID", target = "user.id")
    Post toEntity(PostDTO postDTO);

    @Mapping(source = "post.user.id", target = "userID")
    PostDTO toDto(Post post);
}

package forum.service.mapper;

import forum.entity.Post;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface PostMapper extends EntityMapper<Post, PostDTO> {

    @Mapping(source = "userId", target = "user.id")
    Post toPostFromCreatedPostDTO(CreatedPostDTO createdPostDTO);

    @Mapping(source = "userID", target = "user.id")
    Post toEntity(PostDTO postDTO);

    @Mapping(source = "post.user.id", target = "userID")
    PostDTO toDto(Post post);

}

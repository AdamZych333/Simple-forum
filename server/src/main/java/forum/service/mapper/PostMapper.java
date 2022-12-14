package forum.service.mapper;

import forum.entity.Post;
import forum.entity.User;
import forum.service.dto.CreatedPostDTO;
import forum.service.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface PostMapper extends EntityMapper<Post, PostDTO> {

    Post toPostFromCreatedPostDTO(CreatedPostDTO createdPostDTO);

    @Mapping(source = "userID", target = "user.id")
    Post toEntity(PostDTO postDTO);

    @Mapping(source = "user.id", target = "userID")
    @Mapping(
            target="commentsCount",
            expression="java(post.getComments() != null? post.getComments().size(): 0)"
    )
    PostDTO toDto(Post post);

}

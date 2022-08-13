package forum.service.mapper;

import forum.entity.Comment;
import forum.service.dto.CommentDTO;
import forum.service.dto.CreatedCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<Comment, CommentDTO>{

    Comment toCommentFromCreatedCommentDTO(CreatedCommentDTO createdCommentDTO);

    @Mapping(source = "userID", target = "user.id")
    @Mapping(source = "postID", target = "post.id")
    Comment toEntity(CommentDTO commentDTO);

    @Mapping(source = "user.id", target = "userID")
    @Mapping(source = "post.id", target = "postID")
    CommentDTO toDto(Comment comment);
}

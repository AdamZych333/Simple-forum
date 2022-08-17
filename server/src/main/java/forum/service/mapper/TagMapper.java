package forum.service.mapper;

import forum.entity.Tag;
import forum.service.dto.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<Tag, TagDTO>{

    @Override
    @Mapping(target="count", expression="java(entity.getPosts().size())")
    TagDTO toDto(Tag entity);

}

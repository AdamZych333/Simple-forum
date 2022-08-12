package forum.service.mapper;

import forum.entity.Tag;
import forum.service.dto.TagDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<Tag, TagDTO>{

    Set<Tag> toEntitySetFromDTOList(List<TagDTO> dtoList);
}

package forum.service;

import forum.entity.Tag;
import forum.repository.TagRepository;
import forum.service.dto.TagDTO;
import forum.service.mapper.TagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TagService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagMapper tagMapper, TagRepository tagRepository) {
        this.tagMapper = tagMapper;
        this.tagRepository = tagRepository;
    }

    public void addTags(List<TagDTO> tagDTOS){
        log.debug("Request to add tags {}", tagDTOS);
        if(tagDTOS == null) return;

        for(TagDTO newTag : tagDTOS){
            newTag.setName(
                    newTag.getName()
                            .toLowerCase()
                            .replaceAll(" ", "")
            );
            Tag tag = tagRepository.findByName(newTag.getName());
            if(tag != null){
                newTag.setId(tag.getId());
            }
            else{
                newTag.setId(tagRepository.save(tagMapper.toEntity(newTag)).getId());
            }
        }
    }

    public List<TagDTO> getTags(){
        log.debug("Request to get all tags");

        return tagMapper.toDto(tagRepository.findAll());
    }
}

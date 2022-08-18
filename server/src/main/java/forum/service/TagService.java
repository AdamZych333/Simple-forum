package forum.service;

import forum.entity.Post;
import forum.entity.Tag;
import forum.repository.TagRepository;
import forum.service.dto.CreatedTagDTO;
import forum.service.dto.TagDTO;
import forum.service.mapper.TagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class TagService {

    private final static Logger log = LoggerFactory.getLogger(PostService.class);

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagMapper tagMapper,
                      TagRepository tagRepository
    ) {
        this.tagMapper = tagMapper;
        this.tagRepository = tagRepository;
    }

    public void updateTags(List<CreatedTagDTO> tagDTOS, Post post){
        log.debug("Updating: tags {} in post {}", tagDTOS, post);

        if(tagDTOS == null || post == null) return;

        removeTags(post);
        for(CreatedTagDTO newTag : tagDTOS){
            newTag.setName(newTag.getName()
                    .toLowerCase()
                    .replaceAll(" ", "")
            );
            Tag tag = tagRepository.findByName(newTag.getName());
            if(tag == null){
                tag = tagMapper.toEntity(newTag);
                tag.setPosts(new HashSet<>());
            }
            tag.getPosts().add(post);
            tagRepository.save(tag);
        }
    }

    public void removeTags(Post post){
        log.debug("Removing: tags in post {}", post);

        for(Tag tag : post.getTags()){
            if(tag.getPosts() == null) continue;
            tag.getPosts().removeIf(p -> p.getId().equals(post.getId()));
            if(tag.getPosts().size() == 0){
                tagRepository.delete(tag);
            }else{
                tagRepository.save(tag);
            }
        }
    }

    public List<TagDTO> getTags(int page, int pageSize){
        log.debug("Fetching: tags page {} pageSize {}", page, pageSize);

        List<Tag> tags = tagRepository.findAllByPostsCount(PageRequest.of(page, pageSize));

        return tagMapper.toDto(tags);
    }
}

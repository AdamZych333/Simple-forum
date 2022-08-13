package forum.service.dto;

import forum.config.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class CreatedPostDTO {

    @NotEmpty
    @Size(max=Constants.POST_TITLE_MAX_LENGTH)
    private String title;

    @NotEmpty
    @Size(max=Constants.POST_CONTENT_MAX_LENGTH)
    private String content;

    private List<TagDTO> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}

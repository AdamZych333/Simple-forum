package forum.service.dto;

import forum.config.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreatedCommentDTO {

    @NotEmpty
    @Size(max= Constants.COMMENT_CONTENT_MAX_LENGTH)
    private String content;

    private Long parentID;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }
}

package forum.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddPostDTO {

    @NotEmpty
    @Size(max=300)
    private String content;

    private Long userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

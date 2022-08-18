package forum.service.dto;

import forum.config.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreatedTagDTO {

    @NotNull
    @Size(min= Constants.TAG_MIN_LENGTH, max=Constants.TAG_MAX_LENGTH)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

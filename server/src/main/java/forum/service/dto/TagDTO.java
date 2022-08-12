package forum.service.dto;

import forum.config.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagDTO {

    public Long id;

    @NotNull
    @Size(min= Constants.TAG_MIN_LENGTH, max=Constants.TAG_MAX_LENGTH)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
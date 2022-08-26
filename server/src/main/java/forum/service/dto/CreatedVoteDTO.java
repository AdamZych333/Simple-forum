package forum.service.dto;

import forum.config.Constants;

import javax.validation.constraints.NotNull;

public class CreatedVoteDTO {


    @NotNull
    private Constants.Vote type;

    public Constants.Vote getType() {
        return type;
    }

    public void setType(Constants.Vote type) {
        this.type = type;
    }
}

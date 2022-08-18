package forum.service.dto;

import forum.config.Constants;

public class CreatedVoteDTO {

    private Constants.Vote type;

    public Constants.Vote getType() {
        return type;
    }

    public void setType(Constants.Vote type) {
        this.type = type;
    }
}

package forum.service.dto;

import forum.config.Constants;

public class VoteDTO {

    private Constants.Vote type;

    private int count;

    public Constants.Vote getType() {
        return type;
    }

    public void setType(Constants.Vote type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

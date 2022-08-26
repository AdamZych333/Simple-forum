package forum.service.dto;

import forum.config.Constants;

public class VotesDTO {

    private Constants.Vote type;

    private long postID;

    private int count;

    private boolean isVoted;

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

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public long getPostID() {
        return postID;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }
}

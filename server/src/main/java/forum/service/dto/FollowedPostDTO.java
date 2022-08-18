package forum.service.dto;

public class FollowedPostDTO extends PostDTO{

    long newActivity;

    public FollowedPostDTO(PostDTO postDTO) {
        this.setContent(postDTO.getContent());
        this.setTitle(postDTO.getTitle());
        this.setId(postDTO.getId());
        this.setTags(postDTO.getTags());
        this.setCreatedAt(postDTO.getCreatedAt());
        this.setLastModificationAt(postDTO.getLastModificationAt());
        this.setUserID(postDTO.getUserID());
        this.setCommentsCount(postDTO.getCommentsCount());
    }

    public long getNewActivity() {
        return newActivity;
    }

    public void setNewActivity(long newActivity) {
        this.newActivity = newActivity;
    }
}

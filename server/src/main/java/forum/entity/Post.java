package forum.entity;

import forum.config.Constants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false, length = Constants.POST_TITLE_MAX_LENGTH)
    private String title;

    @NotNull
    @Column(name = "content", nullable = false, length = Constants.POST_CONTENT_MAX_LENGTH)
    private String content;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @NotNull
    @Column(name = "last_modification_at", nullable = false)
    private Timestamp lastModificationAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "posts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Follow> follows;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastModificationAt() {
        return lastModificationAt;
    }

    public void setLastModificationAt(Timestamp lastModificationAt) {
        this.lastModificationAt = lastModificationAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Follow> getFollows() {
        return follows;
    }

    public void setFollows(Set<Follow> follows) {
        this.follows = follows;
    }
}

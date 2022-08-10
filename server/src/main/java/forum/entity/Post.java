package forum.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import forum.config.Constants;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

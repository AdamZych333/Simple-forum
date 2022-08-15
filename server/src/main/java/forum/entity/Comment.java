package forum.entity;

import forum.config.Constants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false, length = Constants.COMMENT_CONTENT_MAX_LENGTH)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Comment> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Set<Comment> getChildren() {
        return children;
    }

    public void setChildren(Set<Comment> children) {
        this.children = children;
    }
}

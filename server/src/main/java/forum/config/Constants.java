package forum.config;

public interface Constants {
    enum Role{
        ADMIN("ADMIN"), USER("USER");

        public final String name;

        Role(String name) {
            this.name = name;
        }
    }

    enum Vote {
        UP("UP"), DOWN("DOWN");

        public final String name;

        Vote(String name) {this.name = name; }
    }
    int POST_CONTENT_MAX_LENGTH = 700;
    int POST_TITLE_MAX_LENGTH = 50;
    int COMMENT_CONTENT_MAX_LENGTH = 400;
    int TAG_MAX_LENGTH = 20;
    int TAG_MIN_LENGTH = 1;
    String ALLOWED_ORIGIN = "http://localhost:4200";
}

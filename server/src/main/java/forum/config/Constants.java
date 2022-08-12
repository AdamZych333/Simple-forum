package forum.config;

public interface Constants {
    enum Role{
        ADMIN("ADMIN"), USER("USER");

        public String name;

        Role(String name) {
            this.name = name;
        }
    }
    int POST_CONTENT_MAX_LENGTH = 700;
    int POST_TITLE_MAX_LENGTH = 50;
    int TAG_MAX_LENGTH = 20;
    int TAG_MIN_LENGTH = 1;
}

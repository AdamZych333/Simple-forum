package forum.service.dto;

import com.sun.istack.NotNull;
import forum.service.annotation.validation.ValidPassword;

public class UpdateUserDTO {

    @NotNull
    private String name;

    @NotNull
    @ValidPassword
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

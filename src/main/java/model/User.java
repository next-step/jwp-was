package model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    @Builder
    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean authenticate(final String userId, final String password) {
        return StringUtils.equals(this.userId, userId) &&
                StringUtils.equals(this.password, password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}

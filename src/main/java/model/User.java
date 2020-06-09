package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.MapUtil;

import java.util.Map;

@NoArgsConstructor
@Getter
public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(Map<String, String> queryString) {
        return MapUtil.convertToObject(queryString, User.class);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    public boolean isAuthentic(String password) {
        return this.password.equals(password);
    }
}

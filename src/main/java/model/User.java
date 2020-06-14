package model;

import com.sun.jndi.toolkit.url.UrlUtil;
import http.QueryMap;

import javax.annotation.Nullable;
import java.net.MalformedURLException;

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

    private User(QueryMap map) {
        this.userId = map.getString("userId");
        this.password = map.getString("password");
        this.name = map.getString("name");
        try {
            this.email = UrlUtil.decode(map.getString("email"));
        } catch (MalformedURLException e) {
            this.email = "";
        }
    }

    @Nullable
    public static User newInstance(QueryMap map) {
        if (map.isEmpty()) {
            return null;
        }
        return new User(map);
    }

    public boolean isAuthuorizedUser(String userId, String password) {
        return this.userId.equalsIgnoreCase(userId) && this.password .equalsIgnoreCase(password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}

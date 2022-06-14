package model;

import com.google.common.base.Strings;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User from(Map<String, String> map) {
        // 추후 추상화 시 사용
        User user = new User();
        ReflectionUtils.doWithFields(user.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            String value = map.get(field.getName());
            if (Strings.isNullOrEmpty(value)) {
                throw new IllegalUserInputException(field.getName(), value);
            }
            ReflectionUtils.setField(field, user, value);
        });

        return user;
    }

    public boolean matchPassword(String input) {
        return password.equals(input);
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

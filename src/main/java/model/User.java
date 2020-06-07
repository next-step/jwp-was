package model;

import java.util.HashMap;
import java.util.Map;

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

    public static User of(String requestParameter) {
        String[] parameters = requestParameter.split("&");
        int index = 0;
        Map parameterMap = new HashMap();
        while(index < parameters.length) {
            String[] keyvalue = parameters[index].split("=");
            parameterMap.put(keyvalue[0], keyvalue[1]);
            index++;
        }
        return new User(parameterMap.get("userId").toString(), parameterMap.get("password").toString(),
                parameterMap.get("name").toString(), parameterMap.get("email").toString());
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

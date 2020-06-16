package model;

import org.springframework.util.StringUtils;

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
        if (StringUtils.isEmpty(requestParameter)) return null;
        String[] parameters = requestParameter.split("&");
        int index = 0;
        Map<String, String> parameterMap = new HashMap();
        while(index < parameters.length) {
            String[] keyvalue = parameters[index].split("=");
            parameterMap.put(keyvalue[0], null);
            if (keyvalue.length == 2)
                parameterMap.put(keyvalue[0], keyvalue[1]);
            index++;
        }
        if (StringUtils.isEmpty(parameterMap.get("userId")) ||
                StringUtils.isEmpty(parameterMap.get("password")))
            return null;
        return new User(parameterMap.get("userId"), parameterMap.get("password"),
                parameterMap.get("name"), parameterMap.get("email"));
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

package utils;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
    public static User convertToUser(String queryString) {
        Map<String, String> requestMap = new HashMap<>();
        for (String couple : queryString.split("\\&")) {
            String[] keyValue = couple.split("=");
            requestMap.put(keyValue[0], keyValue[1]);
        }

        return new User(requestMap.get("userId"), requestMap.get("password"), requestMap.get("name"), requestMap.get("email"));
    }
}

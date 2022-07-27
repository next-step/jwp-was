package webserver.response.post;

import static model.User.*;
import static model.User.DEFAULT_VALUE;

import java.util.HashMap;
import java.util.Map;

import model.LoginRequest;
import model.User;
import model.UserException;

public class UserParser {
    private static final int KEY_VALUE_LENGTH = 2;
    private static final String REQUEST_BODY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private UserParser() {}

    public static LoginRequest parseToLoginRequest(String requestBody) {
        String[] userInfo = requestBody.split(REQUEST_BODY_DELIMITER);
        Map<String, String> userInfoMap = createUserInfoMap(userInfo);

        return new LoginRequest(
                userInfoMap.getOrDefault(FIELD_USER_ID, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_PASSWORD, DEFAULT_VALUE)
        );
    }

    public static User parseToUser(String requestBody) {
        String[] userInfo = requestBody.split(REQUEST_BODY_DELIMITER);
        Map<String, String> userInfoMap = createUserInfoMap(userInfo);

        return new User(
                userInfoMap.getOrDefault(FIELD_USER_ID, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_PASSWORD, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_NAME, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_EMAIL, DEFAULT_VALUE)
        );
    }

    private static Map<String, String> createUserInfoMap(String[] userInfo) {
        Map<String, String> userInfoMap = new HashMap<>();

        for (String info : userInfo) {
            String[] keyValue = info.split(KEY_VALUE_DELIMITER);

            checkUserInfoLength(keyValue);

            String key = keyValue[0];
            String value = keyValue[1];

            userInfoMap.put(key, value);
        }

        return userInfoMap;
    }

    private static void checkUserInfoLength(String[] keyValue) {
        if (keyValue.length != KEY_VALUE_LENGTH) {
            throw new UserException("userInfo가 유효하지 않습니다.");
        }
    }
}

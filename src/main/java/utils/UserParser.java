package utils;

import java.util.HashMap;
import java.util.Map;

import model.LoginUser;
import model.User;
import utils.exception.UserParserException;

public class UserParser {
    private static final String FIELD_USER_ID = "userId";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_EMAIL = "email";
    private static final String DEFAULT_VALUE = "";
    private static final int KEY_VALUE_LENGTH = 2;
    private static final String REQUEST_BODY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private UserParser() {}

    public static User createUser(String requestBody) {
        String[] userInfo = requestBody.split(REQUEST_BODY_DELIMITER);
        Map<String, String> userInfoMap = createUserInfoMap(userInfo);

        return new User(
                userInfoMap.getOrDefault(FIELD_USER_ID, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_PASSWORD, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_NAME, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_EMAIL, DEFAULT_VALUE)
        );
    }

    public static LoginUser createLoginUser(String requestBody) {
        String[] userInfo = requestBody.split(REQUEST_BODY_DELIMITER);
        Map<String, String> userInfoMap = createUserInfoMap(userInfo);

        return new LoginUser(
                userInfoMap.getOrDefault(FIELD_USER_ID, DEFAULT_VALUE),
                userInfoMap.getOrDefault(FIELD_PASSWORD, DEFAULT_VALUE)
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
            throw new UserParserException("userInfo가 유효하지 않습니다.");
        }
    }
}

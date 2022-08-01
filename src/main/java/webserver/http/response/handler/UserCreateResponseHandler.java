package webserver.http.response.handler;

import static model.User.*;
import static model.User.DEFAULT_VALUE;

import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import model.UserException;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class UserCreateResponseHandler implements ResponseHandler {
    private static final String REDIRECT_INDEX_HTML = "/index.html";
    private static final int KEY_VALUE_LENGTH = 2;
    private static final String REQUEST_BODY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    @Override
    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        User user = createUser(requestBody);

        DataBase.addUser(user);

        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.FOUND)
                .addContentType(ContentType.HTML)
                .addContentLength(responseBody.length)
                .addLocation(REDIRECT_INDEX_HTML)
                .toString();
    }

    private User createUser(String requestBody) {
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

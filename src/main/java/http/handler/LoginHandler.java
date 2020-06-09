package http.handler;

import com.google.common.collect.Maps;
import db.DataBase;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.request.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static http.common.HttpHeader.LOCATION_HEADER_NAME;

@Slf4j
public class LoginHandler extends AbstractHandler {
    private static final String INDEX_PATH = "/index.html";

    private static final String SET_COOKIE_HEADER_NAME = "Set-Cookie";
    private static final String LOGIN_SUCCESS_COOKIE_VALUE = "logined=true";
    private static final String LOGIN_FAIL_COOKIE_VALUE = "logined=false";
    private static final String ROOT_PATH_COOKIE_VALUE = "Path=/";
    private static final String COOKIE_VALUE_DELIMITER = "; ";

    private static final String USER_ID_KEY = "userId";
    private static final String PASSWORD_KEY = "password";

    @Override
    public String getPath() {
        return INDEX_PATH;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FOUND;
    }

    @Override
    protected HttpHeaders getHttpHeaders(HttpRequest httpRequest, int length) {
        Map<String, String> httpHeaders = Maps.newHashMap();

        if (isAuthenticUser(httpRequest)) {
            httpHeaders.put(LOCATION_HEADER_NAME, getPath());
            httpHeaders.put(SET_COOKIE_HEADER_NAME, getCookieValue(LOGIN_SUCCESS_COOKIE_VALUE));
        }
        else {
            httpHeaders.put(LOCATION_HEADER_NAME, LOGIN_FAILED_PATH);
            httpHeaders.put(SET_COOKIE_HEADER_NAME, getCookieValue(LOGIN_FAIL_COOKIE_VALUE));
        }

        return new HttpHeaders(httpHeaders);
    }

    private String getCookieValue(String cookieValue) {
        return cookieValue + COOKIE_VALUE_DELIMITER + ROOT_PATH_COOKIE_VALUE;
    }

    @Override
    public byte[] getHttpResponseBody(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (isAuthenticUser(httpRequest)) {
            return super.getHttpResponseBody(httpRequest);
        }

        return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + LOGIN_FAILED_PATH);
    }

    private boolean isAuthenticUser(HttpRequest httpRequest) {
        return isAuthenticUser(
            getBodyMapValue(httpRequest, USER_ID_KEY),
            getBodyMapValue(httpRequest, PASSWORD_KEY)
        );
    }

    private boolean isAuthenticUser(String userId, String password) {
        return Optional.ofNullable(DataBase.findUserById(userId))
            .filter(user -> user.isAuthentic(password))
            .isPresent();
    }

    private String getBodyMapValue(HttpRequest httpRequest, String key) {
        return httpRequest.getBodyMapValue(key);
    }
}

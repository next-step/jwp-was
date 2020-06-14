package http.handler;

import com.google.common.collect.Maps;
import db.DataBase;
import http.common.*;
import http.request.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static http.common.Cookies.*;
import static http.common.HttpHeader.LOCATION_HEADER_NAME;
import static http.common.HttpHeaders.SET_COOKIE_HEADER_NAME;

@Slf4j
public class LoginHandler extends AbstractHandler {
    private static final String INDEX_PATH = "/index.html";

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

            Cookies cookies = getLoginSuccessCookies();
            httpHeaders.put(SET_COOKIE_HEADER_NAME, cookies.toString());
        }
        else {
            httpHeaders.put(LOCATION_HEADER_NAME, LOGIN_FAILED_PATH);
        }

        return new HttpHeaders(httpHeaders);
    }

    private Cookies getLoginSuccessCookies() {
        HttpSession session = HttpSessionUtil.getSession();
        Map<String, String> cookiesMap = Maps.newHashMap();
        cookiesMap.put(SESSION_ID_COOKIE_NAME, session.getId());
        return new Cookies(cookiesMap);
    }

    @Override
    public byte[] getHttpResponseBody(HttpRequest request) throws IOException, URISyntaxException {
        if (isAuthenticUser(request)) {
            return super.getHttpResponseBody(request);
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
        return httpRequest.getParameterMapValue(key);
    }
}

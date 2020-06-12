package http.handler;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.collect.Maps;
import db.DataBase;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.handler.mapper.StaticResource;
import http.request.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static http.common.Cookies.COOKIE_HEADER_NAME;
import static http.common.Cookies.LOGIN_SUCCESS_COOKIE_VALUE;
import static http.common.HttpHeader.LOCATION_HEADER_NAME;

@Slf4j
public class ListUserHandler extends AbstractHandler {
    private static final String USER_LIST_PATH = "/user/list.html";
    private static final String TEMPLATES_PREFIX = "/templates";

    public static final String USER_LIST_LOCATION = "user/list";
    public static final String NOT_LOGIN_LOCATION = "user/login";

    @Override
    public String getPath() {
        return USER_LIST_PATH;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FOUND;
    }

    @Override
    protected HttpHeaders getHttpHeaders(HttpRequest httpRequest, int length) {
        Map<String, String> httpHeaders = Maps.newHashMap();

        if (!isLoginedUser(httpRequest)) {
            httpHeaders.put(LOCATION_HEADER_NAME, LOGIN_FAILED_PATH);
        }

        return new HttpHeaders(httpHeaders);
    }

    @Override
    public byte[] getHttpResponseBody(HttpRequest response) throws IOException {
        String location = NOT_LOGIN_LOCATION;
        Map<String, List<User>> users = Maps.newHashMap();

        if (isLoginedUser(response)) {
            location = USER_LIST_LOCATION;
            users.put("users", DataBase.findAllUserList());
        }

        return compileTemplate(location, users).getBytes();
    }

    private boolean isLoginedUser(HttpRequest httpRequest) {
        return isLoginedUser(httpRequest.getHeaderValue(COOKIE_HEADER_NAME));
    }

    private boolean isLoginedUser(String cookieValue) {
        return Optional.ofNullable(cookieValue)
            .filter(value -> value.contains(LOGIN_SUCCESS_COOKIE_VALUE))
            .isPresent();
    }

    String compileTemplate(String location, Map<String, List<User>> users) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PREFIX);
        loader.setSuffix(StaticResource.HTML.getSuffix());
        return new Handlebars(loader).compile(location).apply(users);
    }
}

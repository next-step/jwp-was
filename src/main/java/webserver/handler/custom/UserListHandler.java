package webserver.handler.custom;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.Headers;
import http.request.Request;
import http.response.Response;
import http.response.ResponseBody;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static http.response.ContentType.HTML;
import static http.response.HttpStatus.FOUND;

public class UserListHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(UserListHandler.class);

    private static final String LOCATION = "Location";
    private static final String USERS = "users";
    private static final String PREFIX_TEMPLATES = "/templates";
    private static final String SUFFIX_HTML = ".html";
    private static final String COOKIE = "Cookie";
    private static final String COOKIE_LOGIN_SUCCESS = "logined=true";

    private String url;

    public UserListHandler(String url) {
        this.url = url;
    }

    @Override
    public boolean isSameUrl(String url) {
        return this.url.equals(url);
    }

    @Override
    public Response work(Request request) throws IOException {
        String cookie = request.getHeader(COOKIE);
        Map<String, String> headers = new HashMap<>();

        if (cookie == null || !cookie.contains(COOKIE_LOGIN_SUCCESS)) {
            headers.put(LOCATION, "/user/login_failed.html");
            return new Response(FOUND, HTML, new Headers(headers), getBody("user/login_failed"));
        }

        return new Response(FOUND, HTML, new Headers(headers), getBody("user/list"));
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    private ResponseBody getBody(String location) throws IOException {
        Map<String, List<User>> map = new HashMap<>();
        if(!location.contains("fail")){
            map.put(USERS, DataBase.getAllUsers());
        }
        String profile = compileTemplate(location).apply(map);
        logger.debug("profile : {}", profile);

        return new ResponseBody(profile.getBytes());
    }

    private Template compileTemplate(String location) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX_TEMPLATES);
        loader.setSuffix(SUFFIX_HTML);
        return new Handlebars(loader).compile(location);
    }
}

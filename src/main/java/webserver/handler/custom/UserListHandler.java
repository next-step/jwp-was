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
import webserver.handler.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static http.response.ContentType.HTML;
import static http.response.HttpStatus.FOUND;

public class UserListHandler implements Handler {
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
        String cookie = request.getParameter("Cookie");
        Map<String, String> headers = new HashMap<>();

        if (cookie == null || !cookie.contains("logined=true")) {
            headers.put("Location", "/user/login.html");
            return new Response(FOUND, HTML, new Headers(headers), getBody("user/login"));
        }

        return new Response(FOUND, HTML, new Headers(headers), getBody("user/list"));
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    private ResponseBody getBody(String location) throws IOException {
        Map<String, List<User>> map = new HashMap<>();
        map.put("users", DataBase.getAllUsers());
        String profile = compileTemplate(location).apply(map);
        return new ResponseBody(profile.getBytes());
    }

    private Template compileTemplate(String location) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        return new Handlebars(loader).compile(location);
    }
}

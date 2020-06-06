package webserver.customhandler;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.Request;
import http.request.headers.Headers2;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import http.response.ResponseBody;
import model.User;
import webserver.Handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Response work(Request request) throws UnsupportedEncodingException, URISyntaxException, IOException {
        String cookie = request.getParameter("Cookie");
        Map<String, String> headers = new HashMap<>();
        if (cookie == null || !cookie.contains("logined=true")) {
            headers.put("Location", "/user/login.html");
            return new Response(HttpStatus.FOUND, ContentType.HTML, new Headers2(headers), getBody("user/login"));
        }
        return new Response(HttpStatus.OK, ContentType.HTML, new Headers2(headers), getBody("user/list"));
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

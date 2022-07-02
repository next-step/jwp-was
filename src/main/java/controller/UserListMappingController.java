package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import http.HttpStatus;
import http.MediaType;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListMappingController extends RequestMappingControllerAdapter {
    @Override
    public boolean checkUrl(String url) {
        return "/user/list.html".equals(url);
    }

    @Override

    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        if (!checkLogin(getCookie(httpRequest))) {
            return new HttpResponse(HttpStatus.FOUND, MediaType.TEXT_HTML_UTF8, "/index.html", null);

        }

        return new HttpResponse(HttpStatus.OK, MediaType.TEXT_HTML_UTF8, "/user/list.html", rendering(DataBase.findAll()));
    }

    private String rendering(Collection<User> users) throws IOException {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("users", users);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("/user/list");
        return template.apply(parameterMap);
    }

    private boolean checkLogin(String cookie) {
        return cookie.indexOf("logined=true") != -1;
    }

    private String getCookie(HttpRequest httpRequest) {
        String cookie = httpRequest.getCookie();

        if (cookie == null) {
            return "";
        }

        return httpRequest.getCookie();
    }
}

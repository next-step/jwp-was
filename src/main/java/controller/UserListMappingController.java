package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.Users;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestMappingControllerAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListMappingController extends RequestMappingControllerAdapter {
    @Override
    public boolean checkUrl(String url) {
        return "/user/list.html".equals(url);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!checkLogin(getCookie(httpRequest))) {
            httpResponse.redirect("/index.html");
            return;
        }

        httpResponse.addHeader("Content-Type", httpRequest.getHeader("Accept"));
        httpResponse.ok(rendering(DataBase.findAll()).getBytes());
    }

    private String rendering(Users users) throws IOException {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("users", users.toList());

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
        String cookie = httpRequest.getHeader("Cookie");

        if (cookie == null) {
            return "";
        }

        return cookie;
    }
}

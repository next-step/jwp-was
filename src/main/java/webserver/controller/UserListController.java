package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import enums.HttpMethod;
import enums.HttpStatusCode;
import java.io.IOException;
import java.util.Collection;
import model.User;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserListController implements Controller {
    private static final String EXECUTABLE_PATH = "/user/list";
    private static final HttpMethod EXECUTABLE_METHOD = HttpMethod.GET;
    private static final String LOGIN_COOKIE_KEY = "logined";
    private static final String VIEW_PATH = "/templates";
    private static final String VIEW_EXTENSION = ".html";

    @Override
    public boolean canExecute(HttpRequest httpRequest) {
        return httpRequest.getPath().equals(EXECUTABLE_PATH) &&
                httpRequest.getMethod().equals(EXECUTABLE_METHOD);
    }

    @Override
    public HttpResponse execute(HttpRequest httpRequest) throws Exception {
        if (!isLogined(httpRequest)) {
            return new HttpResponse(HttpStatusCode.UNAUTHORIZED);
        }

        Template template = getUserListTemplate();

        Collection<User> users = DataBase.findAll();
        String page = template.apply(users);

        return new HttpResponse(HttpStatusCode.OK, page.getBytes());
    }

    private boolean isLogined(HttpRequest request) {
        Cookie cookie = request.getCookie(LOGIN_COOKIE_KEY);

        return cookie != null && !cookie.getValue().equals("false");
    }

    private Template getUserListTemplate() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(VIEW_PATH);
        loader.setSuffix(VIEW_EXTENSION);
        Handlebars handlebars = new Handlebars(loader);

        return handlebars.compile(EXECUTABLE_PATH);
    }
}

package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Cookie;
import model.User;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.ResponseBody;

public class UserListController extends AbstractController {
    private static final String USER_LOGIN_PATH = "user/login.html";
    public static final String CONTENT_TYPE = "text/html";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        Cookie cookie = request.getCookie();
        if (!isLogined(cookie)) {
            response.redirect(USER_LOGIN_PATH);
            return;
        }

        byte[] listPage = makeUserListTemplate();
        response.forward(new ResponseBody(listPage), CONTENT_TYPE);
    }

    private boolean isLogined(Cookie cookie) {
        if (Objects.isNull(cookie) || cookie.isEmpty()) {
            return false;
        }

        return !Boolean.parseBoolean(cookie.get("logined"));
    }

    private byte[] makeUserListTemplate() throws IOException {
        List<User> users = new ArrayList<>(DataBase.findAll());
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix("/templates");
        templateLoader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("user/list");

        return template.apply(users).getBytes();
    }
}

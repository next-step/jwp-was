package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import java.io.IOException;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ListUserController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);
    private static final String REDIRECT_URL = "/user/login.html";
    private static final String COOKIE_KEY = "Cookie";
    private static final String LOGIN_COOKIE = "logined=true";
    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String TEMPLATE_SUFFIX = ".html";

    private final Handlebars handlebars;

    public ListUserController() {
        this.handlebars = new Handlebars(initializeTemplateLoader());
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (isLogined(request)) {
            response.responseBody(compileBody(request.getPath()));
            return;
        }
        response.redirect(REDIRECT_URL);
    }

    private TemplateLoader initializeTemplateLoader() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_PREFIX);
        loader.setSuffix(TEMPLATE_SUFFIX);
        return loader;
    }

    private String compileBody(String path) {
        Users users = Users.ofCollection(DataBase.findAll());
        return compile(path, users);
    }

    private String compile(String path, Users users) {
        try {
            return handlebars.compile(path).apply(users);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    private boolean isLogined(HttpRequest request) {
        return request.getHeader(COOKIE_KEY).contains(LOGIN_COOKIE);
    }
}

package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import java.io.IOException;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltae
 */
public class ListUserController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);

    private static final String REDIRECT_URL = "/index.html";
    private static final String LOGIN_COOKIE_KEY = "logined";
    private static final String LOGIN_COOKIE_STATUS_TRUE = "true";
    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String TEMPLATE_SUFFIX = ".html";

    private final Handlebars handlebars;

    public ListUserController() {
        this.handlebars = new Handlebars(initTemplateLoader());
    }

    private TemplateLoader initTemplateLoader() {
        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_PREFIX);
        loader.setSuffix(TEMPLATE_SUFFIX);
        return loader;
    }

    @Override
    protected void doGET(HttpRequest request, HttpResponse response) {
        if (!isLogined(request)) {
            response.redirect(REDIRECT_URL);
        }

        try {
            final Users users = new Users(DataBase.findAll());
            byte[] body = handlebars.compile(request.getPath()).apply(users).getBytes();
            response.responseHTML(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
            response.redirect(REDIRECT_URL);
        }
    }

    private boolean isLogined(HttpRequest request) {
        HttpSession session = getCurrentSession(request);
        return LOGIN_COOKIE_STATUS_TRUE.equals(session.getAttribute(LOGIN_COOKIE_KEY));
    }
}

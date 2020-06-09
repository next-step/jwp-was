package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import java.util.ArrayList;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ListUserController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            if (request.getHeader("Cookie").contains("logined=true")) {
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);
                Template template = handlebars.compile(request.getPath());
                Users users = new Users(new ArrayList<>(DataBase.findAll()));
                String userList = template.apply(users);
                logger.debug("template result : " + userList);
                response.responseBody(userList);
            } else {
                response.redirect("/user/login.html");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

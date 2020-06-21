package controller.user;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.AbstractController;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        if (!loginCheck(request.getHeader("Cookie"))) {
            response.sendRedirect("/user/login.html");
            return;
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        Collection<User> users = DataBase.findAll();

        String profilePage = template.apply(users);

        byte[] body = profilePage.getBytes();

        response.responseTemplateBody(body);
        //response.forward(request.getPath());
    }

    private boolean loginCheck(String line) {
        boolean result = false;
        String loginStatus = line.split("=")[1];

        if (loginStatus.equals("true"))
            result = true;

        return result;
    }
}

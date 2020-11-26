package controller.user;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.AbstractController;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseCode;
import http.HttpSession;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        if (!loginCheck(request.getHttpSession())) {
            response.addHeader("Location", "/user/login.html");
            response.sendRedirect(HttpResponseCode.REDIRECT_300);
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

    private boolean loginCheck(HttpSession session) {
        boolean result = false;
        User user = (User) session.getAttribute("user");

        if(user != null) {
            result = true;
        }

        return result;
    }
}

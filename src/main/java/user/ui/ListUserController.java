package user.ui;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListUserController extends UserController {
    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if (!httpRequest.isLogin()) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login.html");
            return;
        }

        String path = httpRequest.getPath();
        if (path.equals("/user/list")) {
            httpResponse.applyBody(viewListByTemplate(path, new ArrayList<>(DataBase.findAll())));
            httpResponse.forwardTemplateBody();
        }

    }

    private byte[] viewListByTemplate(String path, List<User> users) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = null;
        try {
            template = handlebars.compile(path);
            String listPage = template.apply(users);
            return listPage.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("template error");
        }
    }
}

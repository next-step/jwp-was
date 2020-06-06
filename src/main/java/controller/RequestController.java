package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import dto.Users;
import http.QueryString;
import http.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewHandler;

import java.io.IOException;

public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public ViewHandler createUser(Request request, ViewHandler viewHandler) {
        userController.createUser(new QueryString(request.getBody()), viewHandler);
        return viewHandler;
    }

    public ViewHandler login(Request request, ViewHandler viewHandler) {
        boolean isSuccessLogin = userController.login(new QueryString(request.getBody()), viewHandler);
        viewHandler.addCookie(String.format("logined=%s", isSuccessLogin));
        return viewHandler;
    }

    public ViewHandler userList(Request request, ViewHandler viewHandler) throws IOException {
        if (request.isLogin()) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Users users = userController.userList(viewHandler);
            Template template = handlebars.compile("user/list");
            String listPage = template.apply(users);
            viewHandler.addTemplate(listPage);
            return viewHandler;
        }

        viewHandler.returnFile("/index.html");
        return viewHandler;
    }

    public ViewHandler request(Request request, ViewHandler viewHandler) {
        viewHandler.returnFile(request.getPath());
        return viewHandler;
    }
}

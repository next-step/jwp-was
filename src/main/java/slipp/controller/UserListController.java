package slipp.controller;

import slipp.db.DataBase;
import slipp.model.User;
import webserver.http.controller.Controller;
import webserver.http.controller.template.TemplateCompiler;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.session.Session;

import java.util.Objects;

public class UserListController implements Controller {
    private static final String USER_ATTRIBUTE_NAME = "user";

    private final TemplateCompiler templateCompiler;

    public UserListController(TemplateCompiler templateCompiler) {
        this.templateCompiler = templateCompiler;
    }

    @Override
    public boolean requires(Request request) {
        return request.hasMethod(Method.GET) && request.hasPath("/user/list");
    }

    @Override
    public Response handle(Request request) {
        Session session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE_NAME);
        if (Objects.isNull(user)) {
            return Response.redirect("/user/login.html");
        }


        request.setAttribute("users", DataBase.findAll());
        String compile = templateCompiler.compile("user/list", request);
        return Response.okWithBody(compile);
    }
}

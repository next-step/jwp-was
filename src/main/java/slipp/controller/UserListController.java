package slipp.controller;

import slipp.db.DataBase;
import webserver.http.domain.controller.Controller;
import webserver.http.domain.controller.template.TemplateCompiler;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

public class UserListController implements Controller {

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
        boolean isLogin = request.getCookie("logined")
                .map(cookie -> cookie.hasValue("true"))
                .orElse(false);

        if (!isLogin) {
            return Response.sendRedirect("/user/login.html");
        }

        request.setAttribute("users", DataBase.findAll());
        String compile = templateCompiler.compile("user/list", request);
        return Response.okWithBody(compile);
    }
}

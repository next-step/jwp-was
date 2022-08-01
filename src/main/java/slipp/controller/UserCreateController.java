package slipp.controller;

import com.google.common.base.Charsets;
import slipp.db.DataBase;
import slipp.model.User;
import webserver.http.domain.controller.Controller;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

public class UserCreateController implements Controller {
    @Override
    public boolean requires(Request request) {
        return request.hasMethod(Method.POST) && request.hasPath("/user/create");
    }

    @Override
    public Response handle(Request request) {
        request.decodeCharacter(Charsets.UTF_8);
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User newUser = new User(userId, password, name, email);
        DataBase.addUser(newUser);

        return Response.sendRedirect("/index.html");
    }
}

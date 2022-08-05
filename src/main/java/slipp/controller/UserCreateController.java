package slipp.controller;

import slipp.exception.UserDuplicationException;
import slipp.model.User;
import slipp.service.UserCreateService;
import webserver.http.controller.Controller;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

public class UserCreateController implements Controller {

    private final UserCreateService userCreateService;

    public UserCreateController(UserCreateService userCreateService) {
        this.userCreateService = userCreateService;
    }

    @Override
    public boolean requires(Request request) {
        return request.hasMethod(Method.POST) && request.hasPath("/user/create");
    }

    @Override
    public Response handle(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User newUser = new User(userId, password, name, email);
        try {
            userCreateService.addUser(newUser);
            return Response.redirect("/index.html");
        } catch (UserDuplicationException e) {
            return Response.redirect("/user/form_failed.html");
        }
    }
}

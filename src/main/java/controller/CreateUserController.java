package controller;

import db.DataBase;
import model.*;

public class CreateUserController implements Controller {

    private static final Path path = Path.of("/user/create");

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatch(HttpMethod.POST, path);
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        DataBase.addUser(user);

        return HttpResponse.found("/index.html");
    }
}

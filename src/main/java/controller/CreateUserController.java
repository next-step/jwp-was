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
                request.getBodyValue("userId"),
                request.getBodyValue("password"),
                request.getBodyValue("name"),
                request.getBodyValue("email")
        );
        DataBase.addUser(user);

        return HttpResponse.found("/index.html");
    }
}

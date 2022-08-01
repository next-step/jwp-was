package controller;

import db.DataBase;
import model.*;

import java.util.Map;

public class CreateUserController implements Controller {

    private static final Path path = Path.of("/user/create");

    @Override
    public boolean matchHttpMethodAndPath(HttpRequest request) {
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

        return HttpResponse.of(
                HttpStatusCode.FOUND,
                ResponseHeader.of(Map.of(HttpHeaders.LOCATION, "/index.html")),
                new byte[0]
        );
    }
}

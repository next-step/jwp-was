package controller;

import db.DataBase;
import model.*;

public class LoginController implements Controller {

    private static final Path path = Path.of("/user/login");

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatch(HttpMethod.POST, path);
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        User user = DataBase.findUserById(request.getBodyValue("userId"));
        if (user == null || !validatePassword(request, user)) {
            return HttpResponse.found("/user/login_failed.html", "logined=false; Path=/");
        }
        return HttpResponse.found("/index.html", "logined=true; Path=/");
    }

    private boolean validatePassword(HttpRequest request, User user) {
        return user.equalsPassword(request.getBodyValue("password"));
    }
}

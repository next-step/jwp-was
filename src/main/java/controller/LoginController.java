package controller;

import db.DataBase;
import model.*;

import java.util.Map;

public class LoginController implements Controller {

    private static final Path path = Path.of("/user/login");

    @Override
    public boolean matchHttpMethodAndPath(HttpRequest request) {
        return request.isMatch(HttpMethod.POST, path);
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        User user = DataBase.findUserById(request.getBodyValue("userId"));
        if (user == null || !equalsPassword(request, user)) {
            return HttpResponse.of(
                    HttpStatusCode.FOUND,
                    ResponseHeader.of(Map.of(
                            HttpHeaders.LOCATION, "/user/login_failed.html",
                            HttpHeaders.SET_COOKIE, "logined=false; Path=/"))
            );
        }

        return HttpResponse.of(
                HttpStatusCode.FOUND,
                ResponseHeader.of(Map.of(
                        HttpHeaders.LOCATION, "/index.html",
                        HttpHeaders.SET_COOKIE, "logined=true; Path=/"))
        );
    }

    private boolean equalsPassword(HttpRequest request, User user) {
        return request.getBodyValue("password").equals(user.getPassword());
    }
}

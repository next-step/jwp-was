package requesthandler;

import db.DataBase;
import model.User;
import webserver.controller.Controller;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

/**
 * Created by hspark on 2019-08-05.
 */
public class UserSignupController implements Controller {
    public static final String URL = "/user/create";


    @Override
    public void action(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getAttribute("userId");
        String password = httpRequest.getAttribute("password");
        String name = httpRequest.getAttribute("name");
        String email = httpRequest.getAttribute("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        httpResponse.redirect("/index.html");
    }

    @Override
    public String getRequestUrl() {
        return URL;
    }
}

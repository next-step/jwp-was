package controller;

import db.DataBase;
import model.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserCreateController implements Controller{


    public static final String REDIRECT_PATH = "/index.html";

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        RequestBody body = request.getBody();
        final User user = new User(body.getFirstValue("userId"), body.getFirstValue("password"), body.getFirstValue("name"), body.getFirstValue("email"));
        DataBase.addUser(user);

        response.redirect(REDIRECT_PATH);
        response.writeResponse();
    }
}

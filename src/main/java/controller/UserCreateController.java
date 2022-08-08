package controller;

import db.DataBase;
import model.*;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserCreateController implements Controller{


    public static final String REDIRECT_PATH = "/index.html";

    @Override
    public HttpResponse process(HttpRequest request) throws IOException, URISyntaxException {
        RequestBody body = request.getBody();
        final User user = new User(body.getOneValue("userId"), body.getOneValue("password"), body.getOneValue("name"), body.getOneValue("email"));
        DataBase.addUser(user);

        return HttpResponse.redirect(REDIRECT_PATH, request.getHeader());
    }
}

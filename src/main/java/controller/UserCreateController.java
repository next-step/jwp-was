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
        DataBase.addUser(User.createUser(request.getBody()));

        return HttpResponse.redirect(REDIRECT_PATH);
    }
}

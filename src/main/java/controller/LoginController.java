package controller;

import db.DataBase;
import model.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController extends AbstractController {

    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    public static final String USER_ID = "userId";
    public static final String LOGIN_SUCCESS_PATH = "/index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        final String userId = request.getBody().getFirstValue(USER_ID);
        final User findUser = DataBase.findUserById(userId);

        if (findUser == null) {
            response.loginRedirect(LOGIN_FAILED_PATH, false);
            response.writeResponse();
            return;
        }
        response.loginRedirect(LOGIN_SUCCESS_PATH, true);
        response.writeResponse();
    }
}

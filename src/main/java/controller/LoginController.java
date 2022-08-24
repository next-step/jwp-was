package controller;

import db.DataBase;
import java.util.Objects;
import model.User;
import model.request.HttpRequest;
import model.response.HttpResponse;

public class LoginController extends AbstractController {
    private static final String LOGIN_FAILED_PATH = "user/login_failed.html";
    private static final String LOGIN_SUCCESS_PATH = "index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getRequestParameters().getValue("userId");
        User user = DataBase.findUserById(userId);

        if (Objects.isNull(user)) {
            response.loginRedirect(LOGIN_FAILED_PATH, false);
            return;
        }
        response.loginRedirect(LOGIN_SUCCESS_PATH, true);
    }
}

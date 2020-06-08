package http.controller.user;

import http.HttpSession;
import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;
import model.user.UserRequestView;
import utils.UserData;

import java.util.Objects;

public class LoginController extends AbstractController {
    private static final String REQUEST_MAPPING_VALUE = "/user/login";
    private static final String SESSION_ID = "JSESSONID";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.methodNotAllowed(request);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        UserRequestView userRequestView = request.getBody(UserRequestView.class);
        User user = UserData.getUser(userRequestView.getUserId());

        if (Objects.isNull(user) || !user.login(userRequestView.getPassword())) {
            response.redirect("/user/login_failed.html");
            response.addCookie("logined", "false");
            response.addCookie("Path", "/");
            return;
        }

        HttpSession session = request.getSession();

        response.redirect("/index.html");
        response.addCookie(SESSION_ID, session.getId());
        response.addCookie("logined", "true");
        response.addCookie("Path", "/");
    }

    @Override
    public boolean useAuthentication() {
        return false;
    }

    @Override
    public String getRequestMappingValue() {
        return REQUEST_MAPPING_VALUE;
    }
}


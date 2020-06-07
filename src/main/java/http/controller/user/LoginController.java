package http.controller.user;

import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;
import model.user.UserRequestView;
import utils.ConvertUtils;
import utils.UserData;

import java.util.Objects;

public class LoginController extends AbstractController {
    private static final String REQUEST_MAPPING_VALUE = "/user/login";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        UserRequestView userRequestView = request.getBody(UserRequestView.class);
        User user = UserData.getUser(userRequestView.getUserId());

        if (Objects.isNull(user) || !user.login(userRequestView.getPassword())) {
            response.redirect("/user/login_failed.html");
            response.setCookie("logined=false; Path=/");
            return;
        }

        response.redirect("/index.html");
        response.setCookie("logined=true; Path=/");
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

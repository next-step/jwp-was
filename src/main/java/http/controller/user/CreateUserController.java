package http.controller.user;

import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;
import utils.UserData;

public class CreateUserController extends AbstractController {
    private static final String REQUEST_MAPPING_VALUE = "/user/create";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.methodNotAllowed(request);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = request.getBody(User.class);

        UserData.save(user);
        response.redirect("/index.html");
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

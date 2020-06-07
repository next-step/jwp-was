package http.controller.user;

import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;
import utils.ConvertUtils;
import utils.UserData;

public class CreateUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = ConvertUtils.convertValue(request.getBody(), User.class);

        UserData.save(user);
        response.redirect("/index.html");
    }

    @Override
    public boolean useAuthentication() {
        return false;
    }
}

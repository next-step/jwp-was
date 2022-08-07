package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class SignUpController extends AbstractController {

    public static final String URL = "/user/create";
    public static final String VIEW_PATH = "./templates/user/form.html";
    private static final String redirectPath = "/index.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest
                .getRequestLine()
                .getUrl()
                .getQueryParameter()
                .getParameters();
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        User user = new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();

        return HttpResponse.getView(VIEW_PATH);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getRequestBody().getContents();
        String userId = body.get("userId");
        String password = body.get("password");
        String name = body.get("name");
        String email = body.get("email");

        User user = new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();

        DataBase.addUser(user);

        return HttpResponse.redirect(redirectPath);
    }
}

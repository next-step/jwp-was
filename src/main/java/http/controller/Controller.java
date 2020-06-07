package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;
import model.user.UserRequestView;
import utils.ConvertUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Controller {
    public static HttpResponse requestMapping(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.getPath().equals("/user/create")) {
            return UserController.saveUser(ConvertUtils.convertValue(httpRequest.getBody(), User.class));
        }

        if (httpRequest.getPath().equals("/user/login")) {
            return UserController.login(ConvertUtils.convertValue(httpRequest.getBody(), UserRequestView.class));
        }

        if (httpRequest.getPath().equals("/user/list.html")) {
            if (httpRequest.loggedIn()) {
                return UserController.list();
            }
            return HttpResponse.redirect("/index.html");
        }

        return HttpResponse.loadFile(httpRequest);
    }
}

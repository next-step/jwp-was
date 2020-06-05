package http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;
import model.user.UserRequestView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class Controller {
    public static HttpResponse requestMapping(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.getPath().equals("/user/create")) {
            return UserController.saveUser(convertValue(httpRequest.getBody(), User.class));
        }

        if (httpRequest.getPath().equals("/user/login")) {
            return UserController.login(convertValue(httpRequest.getBody(), UserRequestView.class));
        }

        if (httpRequest.getPath().equals("/user/list.html")) {
            return UserController.list(httpRequest.loggedIn());
        }

        return HttpResponse.loadFile(httpRequest);
    }

    private static <T> T convertValue(Map map, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, valueType);
    }
}

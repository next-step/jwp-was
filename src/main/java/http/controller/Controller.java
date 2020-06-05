package http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class Controller {
    public static HttpResponse requestMapping(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if(httpRequest.getPath().equals("/user/create")) {
            return UserController.saveUser(convertValue(httpRequest.getBody(), User.class));
        }

        return HttpResponse.loadFile(httpRequest);
    }

    private static <T> T convertValue(Map map, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, valueType);
    }
}

package webserver.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class HandlerMapper {
    private static final String USER_CONTROLLER_PATH = "/user/create";

    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        if (httpRequest.getPath().equals(USER_CONTROLLER_PATH)) {
            return new UserController().execute(httpRequest);
        }
        return new ViewController().execute(httpRequest);
    }
}

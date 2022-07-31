package webserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import enums.HttpStatusCode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import model.User;
import webserver.request.HttpHeader;
import webserver.request.HttpRequest;
import webserver.request.QueryString;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

public class UserController implements Controller {
    @Override
    public Boolean canExecute(HttpRequest httpRequest) {
        return httpRequest.getPath().startsWith("/user/create");
    }

    @Override
    public HttpResponse execute(HttpRequest httpRequest) throws Exception {
        RequestBody body = httpRequest.getBody();
        User user = new User(
                body.getValue("userId"),
                body.getValue("password"),
                body.getValue("name"),
                body.getValue("email")
        );

        HttpHeader headers = new HttpHeader();
        headers.setHeader("Location", "/index.html");

        return new HttpResponse(HttpStatusCode.FOUND, headers, user);
    }
}

package webserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import enums.HttpStatusCode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.QueryString;
import webserver.response.HttpResponse;

public class UserController implements Controller {

    @Override
    public HttpResponse execute(HttpRequest httpRequest) throws Exception {
        QueryString queryString = httpRequest.getQueryString();
        User user = new User(
                queryString.getParam("userId"),
                queryString.getParam("password"),
                queryString.getParam("name"),
                queryString.getParam("email")
        );

        return new HttpResponse(HttpStatusCode.OK, user);
    }
}

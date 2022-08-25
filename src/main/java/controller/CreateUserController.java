package controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.body.HttpRequestBody;
import webserver.http.response.HttpResponse;
import webserver.http.response.start_line.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class CreateUserController implements Controller {

    @Override
    public void process(final HttpRequest request, final HttpResponse response) {
        HttpRequestBody httpRequestBody = request.getHttpRequestBody();

        Map<String, String> body = parseBody(httpRequestBody.getBodyValue());

        User user = new User(body.get("userId")
                , body.get("password")
                , body.get("name")
                , body.get("email"));

        DataBase.addUser(user);
        response.redirect(StatusCode.FOUND, "/index.html");
    }

    private Map<String, String> parseBody(final String bodyValue) {
        Map<String, String> body = new HashMap<>();

        String[] split = bodyValue.split("&");

        for (String value : split) {
            String[] keyValue = value.split("=");
            body.put(keyValue[0], keyValue[1]);
        }

        return body;
    }
}

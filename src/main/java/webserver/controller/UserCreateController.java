package webserver.controller;

import db.DataBase;
import enums.HttpMethod;
import enums.HttpStatusCode;
import model.User;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

public class UserCreateController implements Controller {
    static final String EXECUTABLE_PATH = "/user/create";
    static final HttpMethod EXECUTABLE_METHOD = HttpMethod.POST;

    @Override
    public boolean canExecute(HttpRequest httpRequest) {
        return httpRequest.getPath().equals(EXECUTABLE_PATH) &&
                httpRequest.getMethod().equals(EXECUTABLE_METHOD);
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
        DataBase.addUser(user);

        HttpHeader headers = new HttpHeader();
        headers.setHeader("Location", "/index.html");

        return new HttpResponse(HttpStatusCode.FOUND, headers, user);
    }
}

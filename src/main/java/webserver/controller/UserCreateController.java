package webserver.controller;

import db.DataBase;
import http.request.Protocol;
import model.User;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.StatusLine;

import java.util.Map;

public class UserCreateController implements Controller {

    private static final String LOCATION = "Location";

    @Override
    public HttpResponse service(HttpRequest request) {
        DataBase.addUser(new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
                )
        );
        return HttpResponse.of(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.FOUND),
                HttpHeader.from(Map.of(LOCATION, "/index.html"))
        );
    }
}

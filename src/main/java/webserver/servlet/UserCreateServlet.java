package webserver.servlet;

import db.DataBase;
import java.util.Map;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.utils.HttpHeader;

public class UserCreateServlet implements Servlet {

    @Override
    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> bodyQueryStrings = httpRequest.bodyQueryString();
        User user = new User(
            bodyQueryStrings.get("userId"),
            bodyQueryStrings.get("password"),
            bodyQueryStrings.get("name"),
            bodyQueryStrings.get("email")
        );
        DataBase.addUser(user);

        httpResponse.found();
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.addHeader(HttpHeader.LOCATION, "http://localhost:8080/index.html");
    }

}

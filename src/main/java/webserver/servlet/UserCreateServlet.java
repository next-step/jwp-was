package webserver.servlet;

import db.DataBase;
import java.util.Map;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

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

        httpResponse.found("http://localhost:8080/index.html");
    }

}

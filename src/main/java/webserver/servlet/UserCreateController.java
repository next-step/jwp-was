package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserCreateController implements Controller {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
            httpRequest.getParameter("userId"),
            httpRequest.getParameter("password"),
            httpRequest.getParameter("name"),
            httpRequest.getParameter("email")
        );
        DataBase.addUser(user);

        httpResponse.sendRedirect("/index.html");
    }

}

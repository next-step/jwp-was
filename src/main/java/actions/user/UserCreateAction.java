package actions.user;

import db.DataBase;
import model.User;
import webserver.handler.ActionHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateAction implements ActionHandler {

    private static final String COMPLETE_REDIRECT_URL = "/index.html";

    @Override
    public void actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {
        userCreate(httpRequest, httpResponse);
        httpResponse.sendRedirect(COMPLETE_REDIRECT_URL);
    }

    private void userCreate(HttpRequest httpRequest, HttpResponse httpResponse){
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        DataBase.addUser(new User(userId, password, name, email));
    }

}

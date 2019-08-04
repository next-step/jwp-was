package actions.user;

import db.DataBase;
import enums.HttpStatus;
import model.User;
import webserver.handler.ActionHandler;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreatAction implements ActionHandler<Void> {
    @Override
    public Void actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        DataBase.addUser(new User(userId, password, name, email));


        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.setHttpHeader(HttpHeaders.LOCATION, "/index.html");
        return null;
    }

}

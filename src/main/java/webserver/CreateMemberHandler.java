package webserver;

import db.DataBase;
import model.User;
import webserver.http.*;

public class CreateMemberHandler implements Handler {

    @Override
    public boolean isSupport(Request request) {
        return request.getPath().equals("/create");
    }

    @Override
    public Response handle(Request request) {
        RequestParameters parameters = request.getParameters();

        String userId = parameters.getValue("userId");
        String password = parameters.getValue("password");
        String name = parameters.getValue("name");
        String email = parameters.getValue("email");

        DataBase.addUser(new User(userId, password, name, email));

        return null;
    }

}

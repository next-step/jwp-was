package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.RequestMappingInfo;
import webserver.http.*;

import java.util.Map;

public class CreateMemberHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {
        UrlEncodedBodyParser urlEncodedBodyParser = new UrlEncodedBodyParser();

        Map<String, String> body = urlEncodedBodyParser.parseBody(request.getRequestBody());

        String userId = body.get("userId");
        String password = body.get("password");
        String name = body.get("name");
        String email = body.get("email");

        DataBase.addUser(new User(userId, password, name, email));
        response.sendRedirect("/index.html");
    }

}

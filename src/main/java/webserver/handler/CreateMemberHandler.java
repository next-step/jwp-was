package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.http.*;

import java.util.Map;

public class CreateMemberHandler implements Handler {

    @Override
    public boolean isSupport(Request request) {
        return request.getPath().equals("/user/create") && request.getMethod().isPost();
    }

    @Override
    public Response handle(Request request) {
        UrlEncodedBodyParser urlEncodedBodyParser = new UrlEncodedBodyParser();

        Map<String, String> body = urlEncodedBodyParser.parseBody(request.getRequestBody());

        String userId = body.get("userId");
        String password = body.get("password");
        String name = body.get("name");
        String email = body.get("email");

        DataBase.addUser(new User(userId, password, name, email));

        Response response = new Response();
        response.sendRedirect("/index.html");
        return response;
    }

}

package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.*;

import java.util.Map;

public class CreateMemberHandler implements Handler {

    @Override
    public ModelAndView handle(Request request, Response response) {
        UrlEncodedBodyParser urlEncodedBodyParser = new UrlEncodedBodyParser();

        Map<String, String> body = urlEncodedBodyParser.parseBody(request.getRequestBody());

        String userId = body.get("userId");
        String password = body.get("password");
        String name = body.get("name");
        String email = body.get("email");

        DataBase.addUser(new User(userId, password, name, email));
        return new ModelAndView("redirect:/index.html");
    }

}

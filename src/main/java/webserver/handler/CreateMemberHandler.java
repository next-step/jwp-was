package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.HttpRequest;
import webserver.http.Response;

public class CreateMemberHandler implements Handler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest, Response response) {
        String userId = httpRequest.getBodyValue("userId");
        String password = httpRequest.getBodyValue("password");
        String name = httpRequest.getBodyValue("name");
        String email = httpRequest.getBodyValue("email");

        DataBase.addUser(new User(userId, password, name, email));
        return new ModelAndView("redirect:/index.html");
    }

}

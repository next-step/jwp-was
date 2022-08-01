package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.Request;
import webserver.http.Response;

public class CreateMemberHandler implements Handler {

    @Override
    public ModelAndView handle(Request request, Response response) {
        String userId = request.getBodyValue("userId");
        String password = request.getBodyValue("password");
        String name = request.getBodyValue("name");
        String email = request.getBodyValue("email");

        DataBase.addUser(new User(userId, password, name, email));
        return new ModelAndView("redirect:/index.html");
    }

}

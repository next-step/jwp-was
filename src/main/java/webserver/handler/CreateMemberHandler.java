package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class CreateMemberHandler implements Handler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");
        String name = httpRequest.getBody("name");
        String email = httpRequest.getBody("email");

        DataBase.addUser(new User(userId, password, name, email));
        return new ModelAndView("redirect:/index.html");
    }

}

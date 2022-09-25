package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;
import webserver.http.template.UserList;

import java.util.ArrayList;
import java.util.Collection;

public class UserListController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.requestHeader().loginCheck()) {
            Collection<User> users = DataBase.findAll();
            UserList userList = new UserList(new ArrayList<>(users));
            String template = userList.generateUserListTemplate();
            httpResponse.forwardBody(template);
        }

        httpResponse.sendRedirect("/user/login.html");
    }
}

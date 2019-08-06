package actions.user;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.StringUtils;
import webserver.handler.ActionHandler;
import webserver.handler.ModelView;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserListAction implements ActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserListAction.class);

    @Override
    public void actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {

        if (!isLogin(httpRequest)) {
            httpResponse.redirect("/user/login.html");
            return;
        }

        getUserList(httpRequest, httpResponse);
    }

    private boolean isLogin(HttpRequest httpRequest) {
        return StringUtils.nvl(httpRequest.getHeader(HttpHeaders.COOKIE)).contains("logined=true");
    }

    private void getUserList(HttpRequest httpRequest, HttpResponse httpResponse) {

        Collection<User> users = DataBase.findAll();

        ModelView modelView = httpRequest.getModelView();
        modelView.setView("user/list");
        modelView.addObject("users", users);
    }

}

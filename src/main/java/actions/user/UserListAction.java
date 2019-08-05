package actions.user;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.handler.ModelView;
import webserver.handler.ModelViewActionHandler;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Collection;

public class UserListAction implements ModelViewActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserListAction.class);

    @Override
    public ModelView actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {

        if (!isLogin(httpRequest)) {
            httpResponse.redirect("/user/login.html");
            return null;
        }

        return getUserList();
    }

    private boolean isLogin(HttpRequest httpRequest) {
        return !StringUtils.nvl(httpRequest.getHeader(HttpHeaders.COOKIE)).contains("logined=true");
    }

    private ModelView getUserList() {

        Collection<User> users = DataBase.findAll();

        ModelView modelView = new ModelView("user/list");
        modelView.addObject("users", users);
        return modelView;
    }

}

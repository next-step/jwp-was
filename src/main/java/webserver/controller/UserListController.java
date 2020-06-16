package webserver.controller;

import db.DataBase;
import model.User;
import utils.StringUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestHeaders;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.util.Collection;

public class UserListController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            try {
                doGet(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doGet(HttpRequest request, HttpResponse response) throws IOException {
        RequestHeaders requestHeaders = request.getRequestHeaders();
        String cookie = requestHeaders.get("Cookie");
        if (StringUtils.isNotBlank(cookie) && cookie.contains("logined=true")) {
            Collection<User> users = DataBase.findAll();
            response.addBody(users);
            response.response200();
            return;
        }
        String location = "http://" + request.getRequestHeaders().get("Host") + "/user/login.html";
        response.response302(location);
    }
}

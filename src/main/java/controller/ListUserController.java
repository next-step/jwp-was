package controller;

import db.DataBase;
import exception.InvalidHttpMethodException;
import model.User;
import webserver.*;

import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    private static final String ATTRIBUTE_LOGINED = "logined";

    @Override
    void doPost(HttpRequest request, HttpResponse response) throws InvalidHttpMethodException {
        throw new InvalidHttpMethodException();
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String jsessionid = request.getCookie().getValue("JSESSIONID");
        HttpSessionStorage httpSessionStorage = new HttpSessionStorage();
        HttpSession session = httpSessionStorage.getSession(jsessionid);

        if (!Boolean.parseBoolean((String) session.getAttribute(ATTRIBUTE_LOGINED))) {
            response.sendRedirect("/login.html");
            return;
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        byte[] loaded = HandleBarsTemplate.load("user/list", users).getBytes();

        response.forwardBody(loaded);
        return;
    }
}

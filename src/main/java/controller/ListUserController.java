package controller;

import db.DataBase;
import exception.InvalidHttpMethodException;
import model.User;
import webserver.*;

import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    @Override
    void doPost(HttpRequest request, HttpResponse response) throws InvalidHttpMethodException {
        throw new InvalidHttpMethodException();
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws Exception {
        HttpSession session = request.getJSession();

        if (!request.isLogined(session)) {
            response.sendRedirect("/login.html");
            return;
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        byte[] loaded = HandleBarsTemplate.load("user/list", users).getBytes();

        response.forwardBody(loaded);
        return;
    }
}

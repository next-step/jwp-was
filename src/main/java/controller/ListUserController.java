package controller;

import db.DataBase;
import dto.Users;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.ArrayList;

public class ListUserController extends AbstractController {

    public void doGet(HttpRequest request, HttpResponse response) {
        if (request.isAuthentication()) {
            Users users = new Users(new ArrayList<>(DataBase.findAll()));
            response.returnHandlebar("user/list", users);
            return;
        }

        response.forward("/index.html");
    }
}

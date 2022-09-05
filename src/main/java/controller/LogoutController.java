package controller;

import db.DataBase;
import model.http.HttpRequest;
import model.http.HttpResponse;

public class LogoutController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

        DataBase.removeSession();
    }
}

package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

public class UserCreateController extends Controller {
    private static final String INDEX =  "/index.html";

    @Override
    public HttpResponse doPost(HttpRequest request) throws Exception {
        User user = DataBase.findUserById(request.getParameter("userId"));

        user = new User(request.getBodyParameter("userId")
            , request.getBodyParameter("password")
            , request.getBodyParameter("name")
            , request.getBodyParameter("email"));

        DataBase.addUser(user);

        return HttpResponse.sendRedirect(INDEX);
    }
}

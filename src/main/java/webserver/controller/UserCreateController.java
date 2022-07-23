package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.Parameters;

public class UserCreateController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward("user/form");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        String requestBody = request.getRequestBody();
        createUser(requestBody);
        response.sendRedirect("/index.html");
    }

    private void createUser(String requestBody) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");
        String name = parameters.getParameter("name");
        String email = parameters.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }
}

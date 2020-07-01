package http;

import db.DataBase;
import model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class UserController extends DefaultController {

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        super.handle(request, response);
    }

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        System.out.println("UserController - doGet");
        super.doGet(request, response);
    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        System.out.println("UserController - doPost");

        if (request.getPath().equals("/user/create")) {
            create(request, response);
        }

    }

    private void create(final HttpRequest request, final HttpResponse response) {
        try {
            final Map<String, String> requestBody = request.getRequestBody();
            final String userId = requestBody.get("userId");
            final String password = requestBody.get("password");
            final String name = requestBody.get("name");
            final String email = requestBody.get("email");

            final int changeBefore = DataBase.findAll().size();
            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            final int changeAfter = DataBase.findAll().size();

            if (changeAfter - 1 == changeBefore) {
                response.buildResponseLine(HttpStatus.FOUND);
                response.setContentType("text/html; charset=utf-8");
                response.setResponseBody("/index.html");
                response.print();
                return;
            }

            badRequest(request, response);
        } catch (IOException | URISyntaxException e) {
            badRequest(request, response);
        }
    }

    private void badRequest(final HttpRequest request, final HttpResponse response) {
        try {
            response.buildResponseLine(HttpStatus.BAD_REQUEST);
            response.setContentType("text/html; charset=utf-8");
            response.setResponseBody("/error/4xx.html");
            response.print();
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

}

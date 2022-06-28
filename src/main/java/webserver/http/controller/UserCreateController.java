package webserver.http.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestParameters;
import webserver.http.response.HttpResponse;
import webserver.http.service.post.PostService;

public class UserCreateController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");
        String name = requestBody.get("name");
        String email = requestBody.get("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        httpResponse.redirect("/index.html");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestParameters requestParameters = httpRequest.getRequestParameters();
        User user = new User(requestParameters.get("userId"),
                requestParameters.get("password"),
                requestParameters.get("name"),
                requestParameters.get("email"));

        DataBase.addUser(user);

        httpResponse.redirect("/index.html");
    }
}

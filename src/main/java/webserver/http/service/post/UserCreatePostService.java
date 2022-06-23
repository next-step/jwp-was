package webserver.http.service.post;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

public class UserCreatePostService extends PostService {
    @Override
    protected boolean pathMatch(HttpRequest httpRequest) {
        return httpRequest.getPath()
                          .equals("/user/create");
    }
    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");
        String name = requestBody.get("name");
        String email = requestBody.get("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        httpResponse.redirect("/index.html");
    }
}

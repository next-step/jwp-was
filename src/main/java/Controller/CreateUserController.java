package Controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        DataBase.addUser(User.newInstance(httpRequest.getQueryMap()));
        return HttpResponse.redirectBy302StatusCode("/index.html");
    }
}

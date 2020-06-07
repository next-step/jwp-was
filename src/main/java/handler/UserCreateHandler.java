package handler;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.RedirectView;
import model.User;

public class UserCreateHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        return create(httpRequest);
    }

    private HttpResponse create(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        HttpResponse httpResponse = new HttpResponse(new RedirectView("/index.html"));
        return httpResponse;
    }
}

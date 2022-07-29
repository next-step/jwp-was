package webserver.handler;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.UserBinder;
import webserver.response.HttpResponse;

public class CreateUserController implements Controller {

    @Override
    public HttpResponse handle(final HttpRequest request) {
        final User user = UserBinder.from(request.getRequestBody().getParameters());

        DataBase.addUser(user);

        return HttpResponse.redirect("/index.html");
    }
}

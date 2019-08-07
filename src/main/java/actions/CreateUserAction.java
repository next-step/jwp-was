package actions;

import db.DataBase;
import model.User;
import utils.StringUtils;
import webserver.http.Constants;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.response.HttpStatus;

public class CreateUserAction implements Action {
    @Override
    public HttpResponse execute(HttpRequest request) throws Exception {
        DataBase.addUser(newUser(request));

        HttpResponse response = new HttpResponse(HttpStatus.FOUND);
        response.addHeader("Location", Constants.HELLO_PAGE);
        return response;
    }

    private User newUser(HttpRequest request) {
        try {
            return new User(
                    request.getParameter("userId"),
                    request.getParameter("password"),
                    StringUtils.unescape(request.getParameter("name")),
                    StringUtils.unescape(request.getParameter("email"))
            );
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

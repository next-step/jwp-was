package actions.user;

import db.DataBase;
import enums.HttpStatus;
import model.User;
import webserver.handler.ActionHandler;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Optional;

public class UserLoginAction implements ActionHandler<Void> {
    @Override
    public Void actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        Optional<User> matchedUser = Optional.ofNullable(DataBase.findUserById(userId))
                .filter(user -> user.getPassword().equals(password));

        if(!matchedUser.isPresent()) {
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpResponse.setHttpHeader(HttpHeaders.LOCATION, "/user/login_failed.html");
            httpResponse.setHttpHeader(HttpHeaders.SET_COOKIE, "logined=false; Path=/");
            return null;
        }

        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.setHttpHeader(HttpHeaders.LOCATION, "/index.html");
        httpResponse.setHttpHeader(HttpHeaders.SET_COOKIE, "logined=true; Path=/");

        return null;
    }

}

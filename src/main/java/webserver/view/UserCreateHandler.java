package webserver.view;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.AbstractRequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;

import static webserver.http.HttpHeaders.SET_COOKIE;

public class UserCreateHandler extends AbstractRequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateHandler.class);

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);
        logger.debug("User : {}", user);

        response.addHeader(SET_COOKIE, "logined=false; Path=/");
        response.response302Header("/index.html");
    }
}

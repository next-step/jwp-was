package webserver.view;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.AbstractHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.template.ViewResolver;

import java.io.IOException;

import static webserver.http.HttpHeaders.SET_COOKIE;

public class UserCreateHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateHandler.class);

    public UserCreateHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = new User(request.getRequestBodyParameter("userId"), request.getRequestBodyParameter("password"),
                request.getRequestBodyParameter("name"), request.getRequestBodyParameter("email"));
        DataBase.addUser(user);
        logger.debug("User : {}", user);

        response.addHeader(SET_COOKIE, "logined=false; Path=/");
        response.response302Header("/index.html");
    }
}

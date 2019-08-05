package webserver.view;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.AbstractRequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public class UserCreateHandler extends AbstractRequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateHandler.class);

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
        RequestBody requestBody = request.getRequestBody();

        User user = new User(requestBody.getValue("userId"), requestBody.getValue("password"),
                requestBody.getValue("name"), requestBody.getValue("email"));
        DataBase.addUser(user);
        logger.debug("User : {}", user);

        response.response302Header("/index.html", false);
    }
}

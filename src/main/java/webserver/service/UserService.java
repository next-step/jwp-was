package webserver.service;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ResponseHandler;
import webserver.http.Parameters;
import webserver.http.RequestLine;

import java.io.OutputStream;

public class UserService implements WebService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public void process(OutputStream outputStream, Object... obj) {
        RequestLine requestLine = (RequestLine) obj[0];
        String subPath = requestLine.getPath().getSubPath();
        Parameters parameters = requestLine.getPath().getParameters();

        if ("/create".equals(subPath)) {
            createUser(parameters);
            ResponseHandler.response302(outputStream, requestLine);
            return;
        }

        if ("/login".equals(subPath)) {
            String cookie = login(requestLine, parameters);
            ResponseHandler.response302WithCookie(outputStream, requestLine, cookie);
        }
    }

    protected User createUser(Parameters parameters) {
        User user = User.newInstance(parameters);
        DataBase.addUser(user);
        logger.info("register : {}", user.toString());
        return user;
    }

    protected String login(RequestLine requestLine, Parameters parameters) {
        String cookie = "Set-Cookie: ";
        User user = DataBase.findUserById(parameters.findByKey("userId"));

        if (isOwner(parameters, user)) {
            requestLine.getPath().addSubPath("/success");
            logger.info("login success : {} ", user.toString());
            return cookie + "logined=true; Path=/";
        }

        requestLine.getPath().addSubPath("/fail");
        return cookie + "logined=false;";
    }

    protected boolean isOwner(Parameters parameters, User user) {
        return user != null && user.isCorrectPassword(parameters.findByKey("password"));
    }
}

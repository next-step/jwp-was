package handler;

import db.DataBase;
import dto.UserDto;
import http.common.HttpSession;
import http.request.RequestMessage;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.ResponseMessage;
import model.User;
import org.slf4j.Logger;
import webserver.DynamicContentsFactory;
import webserver.StaticResourceLoader;


import java.io.IOException;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class UserListHandler extends AbstractHandler {

    private static final Logger logger = getLogger(UserListHandler.class);

    private static final UserListHandler INSTANCE = new UserListHandler();

    private UserListHandler() {
    }

    public static Handler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
        if (isAuthenticated(requestMessage)) {
            Collection<User> allUsers = DataBase.findAll();

            byte[] body = DynamicContentsFactory.createHTML("/user/list", allUsers);

            responseMessage.forward(HttpStatus.OK, body, ContentType.HTML);
            return;
        }

        byte[] body = StaticResourceLoader.loadResource("/user/login.html");
        responseMessage.forward(HttpStatus.OK, body, ContentType.HTML);
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {

    }

    private boolean isAuthenticated(RequestMessage requestMessage) {
        HttpSession session = requestMessage.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        return userDto != null;
    }
}
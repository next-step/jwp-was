package handler;

import http.common.HttpSession;
import http.request.RequestMessage;
import http.response.ResponseMessage;


import java.io.IOException;

public class UserLogoutHandler extends AbstractHandler {

    private static final UserLogoutHandler INSTANCE = new UserLogoutHandler();

    private UserLogoutHandler() {
    }

    public static Handler getInstance() {
        return INSTANCE;
    }

    @Override
    void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
        HttpSession session = requestMessage.getSession();

        if (session != null && session.getAttribute("user") != null) {
            session.invalidate();
        }
        responseMessage.redirectTo("/user/login.html");
    }

    @Override
    void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {

    }
}

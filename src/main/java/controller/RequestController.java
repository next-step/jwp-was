package controller;

import http.QueryString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewHandler;

public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public ViewHandler createUser(QueryString queryString, ViewHandler viewHandler) {
        userController.createUser(queryString, viewHandler);
        return viewHandler;
    }

    public ViewHandler login(QueryString queryString, ViewHandler viewHandler) {
        boolean isSuccessLogin = userController.login(queryString, viewHandler);
        viewHandler.addCookie(String.format("logined=%s", isSuccessLogin));
        return viewHandler;
    }
}

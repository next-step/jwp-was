package http.handler.mapper;

import http.handler.CreateUserHandler;
import http.handler.Handler;
import http.handler.ListUserHandler;
import http.handler.LoginHandler;

public class Handlers {
    public static final Handler CREATE_USER_HANDLER = new CreateUserHandler();
    public static final Handler LIST_USER_HANDLER = new ListUserHandler();
    public static final Handler LOGIN_HANDLER = new LoginHandler();
}

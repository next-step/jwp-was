package http.handler;

public class Handlers {
    public static final Handler CREATE_USER_HANDLER = new CreateUserHandler();
    public static final Handler LIST_USER_HANDLER = new ListUserHandler();
    public static final Handler LOGIN_HANDLER = new LoginHandler();
//    public static final Controller EXCEPTION_CONTROLLER = new ExceptionController();
}

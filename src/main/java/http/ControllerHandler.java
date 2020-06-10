package http;


import http.controller.*;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class ControllerHandler {
    public static final PathController DEFAULT = new DefaultController();
    public static final PathController LIST_CONTROLLER = new ListController();
    public static final PathController LOGIN_CONTROLLER = new LoginController();
    public static final PathController USER_CONTROLLER = new UserController();


    public static PathController getControllerProcess(HttpRequest request) {
        switch (request.getPath()) {
            case "/user/create":
                return USER_CONTROLLER;
            case "/user/login":
            case "/user/login.html":
                return LOGIN_CONTROLLER;
            case "/user/list":
                return LIST_CONTROLLER;
            default:
                return DEFAULT;
        }
    }
}

package http;

import http.controller.DefaultController;
import http.controller.LoginController;
import http.controller.PathController;
import http.controller.UserController;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class ControllerHandler {

    public static PathController getPathController(HttpRequest httpRequest){
        switch (httpRequest.getRequestLine().getPath()) {
            case "/user/create" :
                return new UserController(httpRequest);
            case "/user/login" :
                return new LoginController(httpRequest);
            default:
                return new DefaultController(httpRequest);
        }
    }
}

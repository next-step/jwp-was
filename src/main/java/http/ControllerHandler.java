package http;

import http.controller.*;

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
            case "/user/list" :
                return new ListController(httpRequest);
            default:
                return new DefaultController(httpRequest);
        }
    }
}

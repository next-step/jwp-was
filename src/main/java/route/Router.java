package route;

import controller.UserController;
import webserver.WebServerRouter;
import webserver.http.HttpMethod;

/**
 * 유저가 작성하를 라우터
 */
public class Router extends WebServerRouter {
    static {
        add(HttpMethod.POST + "/user/create", UserController::createUser);
        add(HttpMethod.POST + "/user/login", UserController::login);
        add(HttpMethod.GET + "/user/list", UserController::list);
    }
}

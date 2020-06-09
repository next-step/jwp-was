package http.request;

import controller.Controller;
import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;
import controller.PathController;

import java.util.Arrays;

public enum RequestUrl {

    USER_CREATE("/user/create", new CreateUserController()),
    USER_LOGIN("/user/login", new LoginController()),
    USER_LIST("/user/list", new ListUserController()),
    NONE("", new PathController());

    private final String url;
    private final Controller controller;

    RequestUrl(String url, Controller controller) {
        this.url = url;
        this.controller = controller;
    }

    public static RequestUrl findByPath(String path) {
        return Arrays.stream(RequestUrl.values())
                .filter(u -> path.equals(u.url))
                .findAny()
                .orElse(NONE);
    }

    public String getUrl() {
        return url;
    }

    public Controller getController() {
        return controller;
    }
}

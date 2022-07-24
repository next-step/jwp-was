package webserver.config;

import webserver.application.UserService;
import webserver.ui.Controller;
import webserver.ui.FrontController;
import webserver.ui.UserController;
import webserver.ui.WelcomeController;

public class WebConfig {
    private final FrontController frontController;

    public WebConfig() {
        frontController = FrontController.newInstance();

        frontController.addController(welcomeController())
                .addController(userController());
    }

    public FrontController frontController() {
        return frontController;
    }

    private Controller welcomeController() {
        return new WelcomeController();
    }

    private Controller userController() {
        return new UserController(userService());
    }

    private UserService userService() {
        return new UserService();
    }
}

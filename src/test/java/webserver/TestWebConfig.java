package webserver;

import webserver.ui.Controller;
import webserver.ui.FrontController;
import webserver.ui.UserController;
import webserver.ui.WelcomeController;

public class TestWebConfig {
    private final FrontController frontController;

    public TestWebConfig() {
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
        return new UserController();
    }
}

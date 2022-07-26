package webserver;

import webserver.application.UserService;
import webserver.ui.Controller;
import webserver.handlers.ControllerContainerImpl;
import webserver.ui.UserController;
import webserver.ui.WelcomeController;

public class TestWebConfig {
    private final ControllerContainerImpl handleContainer;

    public TestWebConfig() {
        handleContainer = ControllerContainerImpl.newInstance();

        handleContainer.addController(welcomeController())
                .addController(userController());
    }

    public ControllerContainerImpl frontController() {
        return handleContainer;
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

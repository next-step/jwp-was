package configuration;

import controller.HomeController;
import controller.UserController;

import java.util.List;

public class HandlerConfiguration {

    private static final HandlerConfiguration instance = new HandlerConfiguration();

    private final List<Object> controllers = List.of(new UserController(), new HomeController());

    private HandlerConfiguration() {
    }

    public static HandlerConfiguration getInstance() {
        return instance;
    }

    public List<Object> getControllers() {
        return controllers;
    }
}

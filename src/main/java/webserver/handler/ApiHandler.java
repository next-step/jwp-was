package webserver.handler;

import com.google.common.collect.Lists;
import service.Controller;
import service.UserCreateController;
import service.UserListController;
import service.UserLoginController;

import java.util.List;

public class ApiHandler extends Handler {

    private static final List<Controller> CONTROLLERS = Lists.newArrayList(new UserCreateController(), new UserLoginController(), new UserListController());

    @Override
    public List<Controller> getServices() {
        return CONTROLLERS;
    }
}

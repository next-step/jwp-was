package webserver.handler;

import com.google.common.collect.Lists;
import service.Service;
import service.UserCreateService;
import service.UserListService;
import service.UserLoginService;

import java.util.List;

public class ApiHandler extends Handler {

    private static final List<Service> services = Lists.newArrayList(new UserCreateService(), new UserLoginService(), new UserListService());

    @Override
    public List<Service> getServices() {
        return services;
    }
}

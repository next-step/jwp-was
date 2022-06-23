package webserver.handler;

import com.google.common.collect.Lists;
import service.Controller;
import service.ResourceController;

import java.util.List;

public class ResourceHandler extends Handler {

    private static final List<Controller> CONTROLLERS = Lists.newArrayList(new ResourceController());

    @Override
    public List<Controller> getServices() {
        return CONTROLLERS;
    }
}

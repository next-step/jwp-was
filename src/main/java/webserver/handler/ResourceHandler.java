package webserver.handler;

import com.google.common.collect.Lists;
import service.ResourceService;
import service.Service;

import java.util.List;

public class ResourceHandler extends Handler {

    private static final List<Service> services = Lists.newArrayList(new ResourceService());

    @Override
    public List<Service> getServices() {
        return services;
    }
}

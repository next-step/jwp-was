package webserver;

import webserver.controller.Controller;
import webserver.controller.StaticResourceController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class DispatcherController {

    private Map<String, Controller> mappingRegistry;
    private StaticResourceController staticResourceController;

    public DispatcherController() {
        staticResourceController = new StaticResourceController();
    }

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (! mappingRegistry.containsKey(httpRequest.getPath())) {
            staticResourceController.service(httpRequest, httpResponse);
            return;
        }

        Controller controller = mappingRegistry.get(httpRequest.getPath());
        controller.service(httpRequest, httpResponse);
    }

    public void setMappingRegistry(Map<String, Controller> mappingRegistry) {
        this.mappingRegistry = mappingRegistry;
    }
}

package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.provider.ServiceInstanceProvider;
import webserver.servlet.Controller;

import java.util.Map;

public class HttpControllerHandler implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpControllerHandler.class);

    private Map<String, Controller> mappingRegistry;

    public HttpControllerHandler() {
        mappingRegistry = ServiceInstanceProvider.getDefaultControllers();
    }

    public HttpControllerHandler(Map<String, Controller> mappingRegistry) {
        this.mappingRegistry = mappingRegistry;
    }

    @Override
    public boolean supports(HttpRequest httpRequest) {
        return mappingRegistry.containsKey(httpRequest.getPath());
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = mappingRegistry.get(httpRequest.getPath());
        controller.service(httpRequest, httpResponse);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

package http;

import controller.BaseController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.ResourceController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class RequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandler.class);

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    public RequestMappingHandler(final BufferedReader bufferedReader, final DataOutputStream dataOutputStream) throws IOException {
        this.httpRequest = new HttpRequest(bufferedReader);
        this.httpResponse = new HttpResponse(dataOutputStream);
        run();
    }

    private void run() {
        String path = httpRequest.getPath();
        ResourceController resourceController = MappingResources.getResourceController(path);
        if (Objects.nonNull(resourceController)) {
            resourceController.service(httpRequest, httpResponse);
            return;
        }

        BaseController baseController = MappingControllers.getController(httpRequest.getPath());
        baseController.service(httpRequest, httpResponse);
    }
}

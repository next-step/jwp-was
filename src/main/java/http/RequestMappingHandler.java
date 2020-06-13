package http;

import controller.BaseController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

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
        if (path.matches("/css/.*") || path.matches("/fonts/.*") || path.matches("/js/.*") || path.matches("/images/*")) {
            httpResponse.forward(path);
            return;
        }
        handlerByUserController();
    }

    private void handlerByUserController() {
        BaseController baseController = MappingControllers.getController(httpRequest.getPath());
        baseController.service(httpRequest, httpResponse);
    }
}

package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.LoginHandler;
import webserver.controller.UserHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    public static void process(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        logger.debug("request header : {}", request);

        if ("/user/create".equals(request.getRequestUriPath())) {
            new UserHandler().handleRequest(request, response);
        } else if ("/user/login".equals(request.getRequestUriPath())) {
            new LoginHandler().handleRequest(request, response);
        } else if("/user/list".equals(request.getRequestUriPath()) || "/user/list.html".equals(request.getRequestUriPath())) {
            new UserHandler().handleRequest(request, response);
        } else {
            String path = request.getRequestUriPath();

            String resourcePath = ResourceHandler.getResourcePath(path);
            byte [] body = ResourceHandler.loadResource(resourcePath);

            response.response200Header(body.length,ResourceHandler.resourceContentType(path));
            response.responseBody(body);
        }
    }

}

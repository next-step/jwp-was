package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    public static void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        logger.debug("request header : {}", httpRequest);

        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
        }

        if (httpRequest.isGet()) {
           doGet(httpRequest, httpResponse);
        }
    }

    private static void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        MethodRequestHandler requestHandler = new PostRequestHandler();
        requestHandler.handleRequest(httpRequest, httpResponse);
    }

    private static void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        MethodRequestHandler requestHandler = new GetRequestHandler();
        requestHandler.handleRequest(httpRequest, httpResponse);
    }
}

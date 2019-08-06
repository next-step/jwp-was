package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    public void processRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        logger.debug("request header : {}", request);

        RequestMappingHandler handler = RequestMapper.find(request.getRequestUriPath());
        handler.service(request, response);
    }
}

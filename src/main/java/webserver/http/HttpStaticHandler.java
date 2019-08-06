package webserver.http;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static webserver.http.HttpDispatcher.dispatcher;

public class HttpStaticHandler implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpStaticHandler.class);

    @Override
    public boolean supports(HttpRequest httpRequest) {
        return true;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            dispatcher(httpRequest, httpResponse).forward(httpRequest.getPath());
        } catch (HttpException e) {
            logger.error("Http Exception " + e.getHttpStatusCode());
            dispatcher(httpRequest, httpResponse).notFound();
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

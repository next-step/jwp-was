package webserver.http;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;

public class HttpStaticHandler implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpStaticHandler.class);

    @Override
    public boolean supports(HttpRequest httpRequest) {
        return true;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            ModelAndView mav = new ModelAndView(httpRequest.getPath());
            httpResponse.forward(httpRequest, httpRequest.getPath());
        } catch (HttpException e) {
            logger.error("Http Exception " + e.getHttpStatusCode());
            httpResponse.sendError(HttpStatusCode.NOT_FOUND);
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

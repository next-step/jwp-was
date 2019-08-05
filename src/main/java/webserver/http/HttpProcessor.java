package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.List;

import static webserver.provider.ServiceInstanceProvider.getDefaultResponseHandlers;

/**
 * web application using servlet
 */
public class HttpProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private List<HttpHandler> httpHandlers;

    public HttpProcessor() {
        this.httpHandlers = getDefaultResponseHandlers();
    }

    /**
     * access static resource (Web Server)
     * @see HttpStaticHandler
     * access dynamic(servlet) resource (Web Application Server)
     * @see HttpServletHandler
     */
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.info("## start: " + httpRequest.getPath());
        for (HttpHandler httpHandler : httpHandlers) {
            if (httpHandler.supports(httpRequest)) {
                httpHandler.handle(httpRequest, httpResponse);
            }
        }
    }
}

package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHolder;
import webserver.response.ResponseHandler;
import webserver.response.ResponseHolder;

import java.util.List;

import static webserver.provider.ServiceInstanceProvider.getDefaultResponseHandlers;

/**
 * web application using servlet
 */
public class HttpProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private List<ResponseHandler> responseHandlers;

    public HttpProcessor() {
        this.responseHandlers = getDefaultResponseHandlers();
    }

    public void process(RequestHolder requestHolder, ResponseHolder responseHolder) {
        logger.info("## start: " + requestHolder.getPath());
        for (ResponseHandler responseHandler : responseHandlers) {
            if (responseHandler.supports(requestHolder)) {
                responseHandler.handle(requestHolder, responseHolder);
            }
        }
    }
}

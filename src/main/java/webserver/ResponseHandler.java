package webserver;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.response.ResponseHolder;
import webserver.response.ResponseSender;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private ResponseSender responseSender;
    private ResourceHandler resourceHandler;

    public ResponseHandler() {
        this.responseSender = new ResponseSender();
        this.resourceHandler = new ResourceHandler();
    }

    public void handleStaticResource(ResponseHolder responseHolder) {
        try {
            ModelAndView mav = new ModelAndView(responseHolder.getPath(), responseHolder.getAttributes());
            String contents = resourceHandler.getContents(mav);
            responseSender.sendResponse(StatusCode.OK, responseHolder, contents.getBytes());
        } catch (HttpException e) {
            logger.error("Http Exception " + e.getStatusCode());
            byte[] errorMessage = e.getMessage().getBytes();
            responseSender.sendResponse(e.getStatusCode(), responseHolder, errorMessage);
        }
    }

    public void handle(ResponseHolder responseHolder) {
        try {
            if (responseHolder.isRedirect()) {
                responseSender.sendResponse(StatusCode.FOUND, responseHolder);
                return;
            }

            if (StringUtils.isNotBlank(responseHolder.getViewName())) {
                ModelAndView mav = new ModelAndView(responseHolder.getViewName(), responseHolder.getAttributes());
                String contents = resourceHandler.getContents(mav);
                responseSender.sendResponse(StatusCode.OK, responseHolder, contents.getBytes());
            }

            responseSender.sendResponse(StatusCode.OK, responseHolder);
        } catch (HttpException e) {
            logger.error("Http Exception " + e.getStatusCode());
            byte[] errorMessage = e.getMessage().getBytes();
            responseSender.sendResponse(e.getStatusCode(), responseHolder, errorMessage);
        }
    }

}

package webserver.response;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;
import webserver.request.RequestHolder;

public class StaticResponseHandler implements ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(StaticResponseHandler.class);

    private ResponseSender responseSender;

    public StaticResponseHandler() {
        this.responseSender = new ResponseSender();
    }

    @Override
    public boolean supports(RequestHolder requestHolder) {
        return true;
    }

    @Override
    public void handle(RequestHolder requestHolder, ResponseHolder responseHolder) {
        try {
            ModelAndView mav = new ModelAndView(requestHolder.getPath());
            responseSender.send(requestHolder, responseHolder, mav);
        } catch (HttpException e) {
            logger.error("Http Exception " + e.getStatusCode());
            ModelAndView mav = new ModelAndView("/error.html");
            mav.setStatusCode(e.getStatusCode());
            responseSender.send(requestHolder, responseHolder, mav);
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

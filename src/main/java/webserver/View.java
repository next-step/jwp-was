package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHolder;
import webserver.response.ResponseHolder;
import webserver.response.ResponseSender;

public class View {

    private static final Logger logger = LoggerFactory.getLogger(View.class);

    private ResponseSender responseSender;

    public View() {
        this.responseSender = new ResponseSender();
    }

    public void render(RequestHolder requestHolder, ResponseHolder responseHolder, ModelAndView mav) {
        responseSender.send(requestHolder, responseHolder, mav);
    }

}

package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;
import webserver.ResourceHandler;
import webserver.request.RequestHolder;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseSender extends ResponseSupport {

    private static final Logger logger = LoggerFactory.getLogger(ResponseSender.class);

    private ResourceHandler resourceHandler;

    public ResponseSender() {
        this.resourceHandler = new ResourceHandler();
    }

    public void send(RequestHolder requestHolder, ResponseHolder responseHolder, ModelAndView mav) {
        try {
            byte[] contents = resourceHandler.getContents(mav).getBytes();
            responseHeader(requestHolder, responseHolder, mav, contents.length);
            responseBody(responseHolder.getDos(), contents);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(RequestHolder requestHolder, ResponseHolder responseHolder, ModelAndView mav, int lengthOfBodyContent) throws IOException {
        logger.debug("code: {}, message: {}", mav.getStatusCode(), mav.getStatusCode().getMessage());
        DataOutputStream dos = responseHolder.getDos();

        mav.getStatusCode().handleResponse(responseHolder);
        dos.writeBytes(getStatusLine(mav));
        dos.writeBytes(getLocationLine(mav));
        dos.writeBytes(getContentTypeLine(requestHolder));
        dos.writeBytes(getContentLengthLine(lengthOfBodyContent));
        dos.writeBytes(getSetCookieLine(mav.getCookies()));

        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        if (body.length > 0) {
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

}

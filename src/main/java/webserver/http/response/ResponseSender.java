package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;
import webserver.http.HttpResponse;
import webserver.resource.ResourceHandler;
import webserver.http.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseSender extends ResponseSupport {

    private static final Logger logger = LoggerFactory.getLogger(ResponseSender.class);

    private ResourceHandler resourceHandler;
    private DataOutputStream dos;
    private String errorPage;

    public ResponseSender(DataOutputStream dos, String errorPage) {
        this.dos = dos;
        this.errorPage = errorPage;
        this.resourceHandler = new ResourceHandler();
    }

    public void notFound(HttpResponse httpResponse) {
        try {
            byte[] contents = resourceHandler.getContents(new ModelAndView(errorPage)).getBytes();
            responseForwardHeader(HttpStatusCode.NOT_FOUND, httpResponse, contents.length);
            responseBody(contents);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void redirect(HttpResponse httpResponse, ModelAndView mav) {
        try {
            responseRedirectHeader(httpResponse, mav.getViewName());
        } catch (IOException e) {
            notFound(httpResponse);
        }
    }

    public void forward(HttpResponse httpResponse, ModelAndView mav) {
        try {
            byte[] contents = resourceHandler.getContents(mav).getBytes();
            responseForwardHeader(HttpStatusCode.OK, httpResponse, contents.length);
            responseBody(contents);
        } catch (IOException e) {
            notFound(httpResponse);
        }
    }

    private void responseForwardHeader(HttpStatusCode statusCode, HttpResponse httpResponse, int lengthOfBodyContent) throws IOException {
        dos.writeBytes(getStatusLine(statusCode));
        dos.writeBytes(getContentTypeLine(httpResponse));
        dos.writeBytes(getContentLengthLine(lengthOfBodyContent));
        dos.writeBytes(getSetCookieLine(httpResponse.getCookies()));

        dos.writeBytes("\r\n");
    }

    private void responseRedirectHeader(HttpResponse httpResponse, String location) throws IOException {
        dos.writeBytes(getStatusLine(HttpStatusCode.FOUND));
        dos.writeBytes(getLocationLine(location));
        dos.writeBytes(getSetCookieLine(httpResponse.getCookies()));

        dos.writeBytes("\r\n");
    }

    private void responseBody(byte[] body) throws IOException {
        if (body.length > 0) {
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

}

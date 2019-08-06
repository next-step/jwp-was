package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.provider.ConfigurationProvider;
import webserver.resource.ResourceHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpDispatcher extends DispatcherSupport {

    private static final Logger logger = LoggerFactory.getLogger(HttpDispatcher.class);

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private String errorPage;
    private ResourceHandler resourceHandler;

    public static HttpDispatcher dispatcher(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpDispatcher httpDispatcher = new HttpDispatcher(httpRequest, httpResponse);
        httpDispatcher.setResourceHandler(new ResourceHandler());
        return httpDispatcher;
    }

    public HttpDispatcher(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
        this.errorPage = ConfigurationProvider.errorPage();
    }

    public void setResourceHandler(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public void notFound() {
        try {
            byte[] contents = resourceHandler.getContents(new ModelAndView(errorPage)).getBytes();
            responseForwardHeader(HttpStatusCode.NOT_FOUND, contents.length);
            responseBody(contents);
        } catch (IOException e) {
            logger.error("{} Resource Not Found: {}", httpRequest.getPath(), e.getMessage());
            throw new IllegalStateException("Resource Not found");
        }
    }

    public void redirect(String location) {
        try {
            responseRedirectHeader(location);
        } catch (IOException e) {
            notFound();
        }
    }

    public void forward(String location) {
        ModelAndView mav = new ModelAndView(httpRequest.getAttributes(), location);
        try {
            byte[] contents = resourceHandler.getContents(mav).getBytes();
            responseForwardHeader(HttpStatusCode.OK, contents.length);
            responseBody(contents);
        } catch (IOException e) {
            notFound();
        }
    }

    private void responseForwardHeader(HttpStatusCode statusCode, int lengthOfBodyContent) throws IOException {
        DataOutputStream dos = httpResponse.getDataOutputStream();
        dos.writeBytes(getStatusLine(statusCode));
        dos.writeBytes(getContentTypeLine(httpRequest.getResponseContentType()));
        dos.writeBytes(getContentLengthLine(lengthOfBodyContent));
        dos.writeBytes(getSetCookieLine(httpResponse.getCookies()));

        dos.writeBytes("\r\n");
    }

    private void responseRedirectHeader(String location) throws IOException {
        DataOutputStream dos = httpResponse.getDataOutputStream();
        dos.writeBytes(getStatusLine(HttpStatusCode.FOUND));
        dos.writeBytes(getLocationLine(location));
        dos.writeBytes(getSetCookieLine(httpResponse.getCookies()));

        dos.writeBytes("\r\n");
    }

    private void responseBody(byte[] body) throws IOException {
        DataOutputStream dos = httpResponse.getDataOutputStream();
        if (body.length > 0) {
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

}

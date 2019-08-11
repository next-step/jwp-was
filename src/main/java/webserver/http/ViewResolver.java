package webserver.http;

import exception.HttpException;
import webserver.resource.ResourceHandler;

import java.io.IOException;

import static webserver.WebContext.ERROR_PAGE;
import static webserver.WebContext.SESSION_KEY;

public class ViewResolver {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private String errorPage;
    private ResourceHandler resourceHandler;

    public static ViewResolver from(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ViewResolver(httpRequest, httpResponse);
    }

    private ViewResolver(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
        this.setResourceHandler(new ResourceHandler());
        this.errorPage = ERROR_PAGE;
        httpResponse.addCookie(SESSION_KEY, httpRequest.getSessionId());
    }

    public void setResourceHandler(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public void error(HttpStatusCode code) {
        try {
            byte[] contents = resourceHandler.getContents(new ModelAndView(errorPage)).getBytes();
            responseForwardHeader(code, contents.length);
            responseBody(contents);
        } catch (Exception e) {
            throw new RuntimeException("ERROR Control Failed");
        }
    }

    public void redirect(String location) {
        try {
            responseRedirectHeader(location);
        } catch (IOException e) {
            throw new HttpException(HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void forward(String location) {
        ModelAndView mav = new ModelAndView(httpResponse.getAttributes(), location);
        try {
            byte[] contents = resourceHandler.getContents(mav).getBytes();
            responseForwardHeader(HttpStatusCode.OK, contents.length);
            responseBody(contents);
        } catch (IOException e) {
            throw new HttpException(HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void responseForwardHeader(HttpStatusCode statusCode, int lengthOfBodyContent) throws IOException {
        httpResponse.writeStatusLine(statusCode);
        httpResponse.writeContentType(httpRequest.getResponseContentType());
        httpResponse.writeContentLength(lengthOfBodyContent);
        httpResponse.writeCookies();
        httpResponse.writeRN();
    }

    private void responseRedirectHeader(String location) throws IOException {
        httpResponse.writeStatusLine(HttpStatusCode.FOUND);
        httpResponse.writeLocation(location);
        httpResponse.writeCookies();
        httpResponse.writeRN();
    }

    private void responseBody(byte[] body) throws IOException {
        httpResponse.writeBody(body);
    }

}

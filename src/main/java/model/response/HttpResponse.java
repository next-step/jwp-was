package model.response;

import java.io.DataOutputStream;
import java.io.IOException;
import model.HttpHeaders;
import model.HttpProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String EMPTY_SPACE = " ";

    private HttpProtocol httpProtocol;
    private HttpHeaders httpHeaders;
    private String status;
    private String message;
    private ResponseBody responseBody;

    private HttpResponse(HttpProtocol httpProtocol, String status, String message, HttpHeaders httpHeaders) {
        this.httpProtocol = httpProtocol;
        this.status = status;
        this.message = message;
        this.httpHeaders = httpHeaders;
    }

    public static HttpResponse redirect(String location) {
        return new HttpResponse(new HttpProtocol("HTTP/1.1"), "302", "OK", new HttpHeaders().addLocation(location));
    }

    public static HttpResponse loginRedirect(String location, boolean login) {
        return new HttpResponse(new HttpProtocol("HTTP/1.1"), "302", "Found",
                new HttpHeaders().addCookie("logined", String.valueOf(login)).addLocation(location));
    }

    public static HttpResponse forward(ResponseBody responseBody, String contentType) {
        return new HttpResponse(new HttpProtocol("HTTP/1.1"), "200", "OK",
                new HttpHeaders().addContentType(contentType).addContentLength(responseBody.getLength()));
    }

    public static HttpResponse init() {
        return new HttpResponse(new HttpProtocol("HTTP/1.1"), "400", "Bad Request", new HttpHeaders());
    }

    public void writeResponse(DataOutputStream dos) throws IOException {
        try {
            dos.writeBytes(this.httpProtocol.getProtocol() + EMPTY_SPACE + this.status + EMPTY_SPACE + this.message);
            this.httpHeaders.writeOutput(dos);
            this.responseBody.writeOutput(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        dos.flush();
    }
}

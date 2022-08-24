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

    public void redirect(String location) {
        this.httpProtocol = new HttpProtocol("HTTP/1.1");
        this.status = "302";
        this.message = "OK";
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.addLocation(location);
    }

    public void loginRedirect(String location, boolean login) {
        this.httpProtocol = new HttpProtocol("HTTP/1.1");
        this.status = "302";
        this.message = "Found";
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.addCookie("logined", String.valueOf(login));
        this.httpHeaders.addLocation(location);
    }

    public void forward(ResponseBody responseBody, String contentType) {
        this.responseBody = responseBody;
        this.httpProtocol = new HttpProtocol("HTTP/1.1");
        this.status = "200";
        this.message = "OK";
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.addContentType(contentType);
        this.httpHeaders.addContentLength(responseBody.getLength());
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

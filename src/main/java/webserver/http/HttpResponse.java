package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private byte[] body;
    private Cookie cookie;
    private HttpHeaders httpHeaders;
    private Model model;
    private HttpStatus httpStatus;
    private String redirectPath;

    public HttpResponse() {
        this.cookie = new Cookie();
        this.model = new Model();
        this.httpStatus = HttpStatus.OK;
        this.httpHeaders = new HttpHeaders();
    }

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.body = body;
        this.cookie = new Cookie();
        this.model = new Model();
        this.httpStatus = httpStatus;
        this.httpHeaders = new HttpHeaders();
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Model getModel() {
        return model;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public void responseByStatus(DataOutputStream dos) {
        this.httpStatus.getResponseConsumer().accept(this, dos);
    }

    public void setCookie(String key, String value) {
        this.cookie.set(key, value);
    }
}


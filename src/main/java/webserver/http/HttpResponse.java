package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ViewResolver;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private byte[] body;
    private Cookie cookie;
    private HttpHeaders httpHeaders;
    private ModelAndView modelAndView;
    private HttpStatus httpStatus;
    private String redirectPath;

    public HttpResponse() {
        this.cookie = new Cookie();
        this.modelAndView = new ModelAndView();
        this.httpStatus = HttpStatus.OK;
        this.httpHeaders = new HttpHeaders();
    }

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.body = body;
        this.cookie = new Cookie();
        this.modelAndView = new ModelAndView();
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

    public ModelAndView getModelAndView() {
        return modelAndView;
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
        ResponseWriter.valueByHttpStatus(this.httpStatus)
                .ifPresent(responseWriter -> responseWriter.write(this, dos));
    }

    public void setCookie(String key, String value) {
        this.cookie.set(key, value);
    }

    public void setView(String viewName) {
        this.modelAndView.setView(viewName);
        try {
            setBody(ViewResolver.mapping(modelAndView).getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}


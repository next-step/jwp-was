package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpHeader header;
    private ResponseBody body;

    public HttpResponse(HttpHeader header, ResponseBody body) {
        this.header = header;
        this.body = body;
    }

    public HttpResponse(HttpHeader header) {
        this.header = header;
        this.body = ResponseBody.empty();
    }

    public static HttpResponse redirect(String location) {
        final HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
        httpHeader.addRedirectStatus();
        httpHeader.addRedirectLocation(location);
        httpHeader.addEndHeader();

        return new HttpResponse(httpHeader);
    }

    public static HttpResponse success(ResponseBody body, String contentType) {
        final HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
        httpHeader.addSuccessStatus();
        httpHeader.addContentType(contentType);
        httpHeader.addContentLength(body.getLength());
        httpHeader.addEndHeader();

        return new HttpResponse(httpHeader, body);
    }

    public static HttpResponse loginRedirect(String location, Boolean loginValue) {
        final HttpHeader httpHeader = new HttpHeader(new ArrayList<>());
        httpHeader.addRedirectStatus();
        httpHeader.addRedirectLocation(location);
        httpHeader.addCookie("logined", loginValue.toString());

        return new HttpResponse(httpHeader);
    }

    public void writeResponse(DataOutputStream dos) {
        try {
            header.writeOutput(dos);
            body.writeOutput(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}

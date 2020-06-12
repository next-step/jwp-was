package http;

import http.enums.HttpResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream outputStream;
    private HttpResponseCode responseCode;
    private byte[] responseBody;
    private Header headers;
    private Cookie cookie;

    public HttpResponse(DataOutputStream outputStream) {
        this.outputStream = outputStream;
        this.headers = new Header();
        this.cookie = new Cookie();
    }

    private void writeResponse() {
        try {
            this.outputStream.writeBytes(responseCode.makeHeader());
            this.outputStream.writeBytes(cookie.writeCookieValue());
            this.outputStream.writeBytes(headers.makeResponseHeader());
            this.outputStream.write(this.responseBody, 0, responseBody.length);

        } catch (IOException e) {
            log.error("write Response Error : {}", e);
        }
    }

    public void send() {
        try {
            this.outputStream.flush();
        } catch (IOException e) {
            log.error("send Response Error : {}", e);
        }
    }

    public void addHeader(String name, String value) {
        this.headers.addKeyAndValue(name, value);
    }

    public void forword(String path) {
        // 정적인 파일 서비스 하는 메소드(.html, .css 등등)
        try {
            this.responseBody = FileIoUtils.loadFileFromClasspath(path);
            this.responseCode = HttpResponseCode.OK;
            addHeader("Content-Length", String.valueOf(responseBody.length));

            this.writeResponse();
        } catch (IOException | URISyntaxException e) {
            log.error("read File Exception  : {} - {}", path, e);
        }
    }

    public void forwordHandleBar(String template) {
        this.responseBody = template.getBytes();
        this.responseCode = HttpResponseCode.OK;
        addHeader("Content-Length", String.valueOf(responseBody.length));
        this.writeResponse();
    }

    public void sendRedirect(String location) {
        // redirect 시키는 메소드;
        this.responseCode = HttpResponseCode.REDIRECT;
        addHeader("Location", location);
        this.writeResponse();
    }

    public HttpResponseCode getResponseCode() {
        return responseCode;
    }

    public void addCookie(String name, String value) {
        this.cookie.addCookieValue(name, value);
    }
}

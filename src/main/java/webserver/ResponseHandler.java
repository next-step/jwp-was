package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    private static final String PROTOCOL = "HTTP/1.1 ";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String CONTENT_LENGTH = "Content-Length: ";
    private final static String SET_COOKIE_HEADER = "Set-Cookie: ";

    private DataOutputStream dos;
    private HttpResponse response;

    public ResponseHandler(OutputStream out, HttpResponse response) {
        this.dos = new DataOutputStream(out);
        this.response = response;
    }

    public void doResponse(HttpRequest request) {
        if (response.headerIsNull()) {
            loadFile(request);
        }

        responseHeader(dos, response);
        responseBody(dos, response);
    }

    private void loadFile(HttpRequest request) {
        try {
            response.loadFile(request);
        } catch (NullPointerException e) {
            response.notFound(request);
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(PROTOCOL + response.getStatusCode() + " " + response.getStatusName() + " \r\n");
            dos.writeBytes(CONTENT_TYPE + response.getContentType() + "\r\n");
            dos.writeBytes(CONTENT_LENGTH + response.getContentLength() + "\r\n");

            for (Map.Entry<String, String> headerEntry : response.getCustomHeader().entrySet()) {
                String key = headerEntry.getKey();
                String value = headerEntry.getValue();
                dos.writeBytes(key + ": " + value + "\r\n");
            }

            for (String cookie : response.getCookies()) {
                dos.writeBytes(SET_COOKIE_HEADER + cookie + "\r\n");
            }

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse response) {
        try {
            dos.write(response.getBody(), 0, response.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

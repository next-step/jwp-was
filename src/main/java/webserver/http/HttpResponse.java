package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private int defaultStatusCode = 200;
    private String defaultContentType = "text/plain";

    private HttpHeaders httpHeaders;
    private List<Cookie> cookies;
    private int statusCode;
    private String contentType;
    private String location;
    private byte[] body;

    public HttpResponse(int statusCode, HttpHeaders httpHeaders, byte[] body, List<Cookie> cookies) {
        this.statusCode = statusCode;
        this.httpHeaders = httpHeaders;
        this.body = body;
        this.cookies = cookies;
    }

    public static HttpResponse ok(String contentType, byte[] body) {
        return ok(contentType, body, new ArrayList<>());
    }

    public static HttpResponse ok(String contentType, byte[] body, List<Cookie> cookies) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", String.format("%s;charset=utf-8", contentType));
        httpHeaders.add("Content-Length", Integer.toString(body.length));
        return new HttpResponse(200, httpHeaders, body, cookies);
    }

    public static HttpResponse redirect(String location) {
        return redirect(location, new ArrayList<>());
    }

    public static HttpResponse redirect(String location, List<Cookie> cookies) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", location);
        return new HttpResponse(302, httpHeaders, new byte[]{}, cookies);
    }


    public enum StatusLine {
        OK(200, "HTTP/1.1 200 OK"),
        FOUND(302, "HTTP/1.1 302 Found"),
        INTERNAL_SERVER_ERROR(500, "HTTP/1.1 500 Internal Server Error");

        private int statusCode;
        private String statusLine;

        StatusLine(int statusCode, String statusLine) {
            this.statusCode = statusCode;
            this.statusLine = statusLine;
        }

        private boolean isEqaul(int statusCode) {
            return this.statusCode == statusCode;
        }

        public static StatusLine findByStatusCode(int statusCode) {
            return Arrays.stream(StatusLine.values())
                    .filter(x -> x.isEqaul(statusCode))
                    .findFirst()
                    .orElse(INTERNAL_SERVER_ERROR);
        }

        @Override
        public String toString() {
            return statusLine;
        }
    }

    public void sendResponse(OutputStream out) {
        if (out == null) {
            throw new IllegalArgumentException();
        }

        try (DataOutputStream dos = new DataOutputStream(out)){
            dos.writeBytes(StatusLine.findByStatusCode(statusCode) + "\r\n");

            for (String key : httpHeaders.keySet()) {
                dos.writeBytes(String.format("%s: %s\r\n", key, httpHeaders.get(key)));
            }

            for (Cookie cookie : cookies) {
                dos.writeBytes(cookie.toString() + "\r\n");
            }

            dos.writeBytes("\r\n");

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

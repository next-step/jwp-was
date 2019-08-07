package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.response.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final int DEFAULT_STATUS_CODE = 200;
    private final String DEFAULT_CONTENT_TYPE = "text/html";
    private final String DEFAULT_CHARSET = "UTF-8";

    private HttpHeaders httpHeaders;
    private List<Cookie> cookies;
    private HttpStatus status;
    private String contentType;
    private byte[] data;
    private int contentLength;

    public HttpResponse(HttpStatus status, String contentType, byte[] data) {
        this.status = status;
        this.contentType = contentType;
        this.httpHeaders = new HttpHeaders();
        this.cookies = new ArrayList<>();

        setContentType(contentType);

        setData(data);
    }

    public HttpResponse(HttpStatus status) {
        this(status, null, null);
    }

    private void setContentType(String contentType) {
        if (contentType == null) {
            this.contentType = DEFAULT_CONTENT_TYPE;
        } else {
            this.contentType = contentType;
        }
    }

    private void setData(byte[] data) {
        if (data == null) {
            this.data = new byte[]{};
            this.contentLength = 0;
        } else {
            this.data = data;
            this.contentLength = data.length;
        }
    }

    public void addHeader(String key, String value) {
        this.httpHeaders.add(key, value);
    }

    public void addCookie(String key, String value) {
        cookies.add(new Cookie(key, value));
    }

    public void sendResponse(OutputStream out) {
        if (out == null) {
            throw new IllegalArgumentException();
        }

        try (DataOutputStream dos = new DataOutputStream(out)){
            dos.writeBytes(String.format("HTTP/1.1 %s\r\n", status.getDescription()));

            dos.writeBytes(String.format("Content-Type: %s\r\n", contentType));

            for (String key : httpHeaders.getHeaderKeys()) {
                dos.writeBytes(String.format("%s: %s\r\n", key, httpHeaders.get(key)));
            }

            for (Cookie cookie : cookies) {
                dos.writeBytes(String.format("Set-Cookie: %s\r\n", cookie));
            }

            dos.writeBytes("\r\n");

            dos.write(this.data, 0, contentLength);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}

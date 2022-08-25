package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Set;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private HttpStatus httpStatus;
    private HttpHeader headers = new HttpHeader();
    private byte[] bytesBody;
    private DataOutputStream dos;

    public HttpResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpResponse(HttpStatus httpStatus, HttpHeader headers) {
        this.httpStatus = httpStatus;
        this.headers = headers;
    }

    public HttpResponse(HttpStatus httpStatus, HttpHeader headers, byte[] bytesBody) {
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.bytesBody = bytesBody;
    }

    public HttpResponse(OutputStream dos) {
        this.dos = new DataOutputStream(dos);
    }

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        String fileExtension = FileIoUtils.getFileExtension(path);
        String contentType = ContentType.selectContent(fileExtension);
        headers.add(HttpHeader.CONTENT_TYPE, contentType);

        byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates" + path);
        response200Header(bytes.length);
        responseBody(bytes);
    }

    public void forwardBody(byte[] bytes) {
        response200Header(bytes.length);
        responseBody(bytes);
    }

    public void response200Header(int length) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("Content-Length: " + length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            headers.add("Location",location);
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void processHeaders() {
        try {
            Set<String> keys = headers.getKeySet();
            for (String key : keys) {
                dos.writeBytes(key + ": " + headers.getValue(key) + "\r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeader getHeaders() {
        return headers;
    }

    public byte[] getBytesBody() {
        return bytesBody;
    }
}


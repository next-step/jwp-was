package response;

import header.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private byte[] body;

    private HttpResponse(HttpStatus httpStatus, HttpHeaders httpHeaders, byte[] body) {
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public static HttpResponse success(byte[] bytes) {
        return new HttpResponse(HttpStatus.OK, HttpHeaders.of(ContentType.TEXT_HTML, new ContentLength(bytes.length)), bytes);
    }

    public static HttpResponse redirect(byte[] bytes, String targetUri) {
        return new HttpResponse(HttpStatus.FOUND, HttpHeaders.of(new Location(targetUri)), bytes);
    }

    public static HttpResponse loginFail(byte[] bytes) {
        return new HttpResponse(HttpStatus.OK, HttpHeaders.of(Cookie.logined(true)), bytes);
    }

    public static HttpResponse loginSuccess(byte[] bytes) {
        return new HttpResponse(HttpStatus.OK, HttpHeaders.of(Cookie.logined(true)), bytes);
    }

    public static HttpResponse css(byte[] bytes) {
        return new HttpResponse(HttpStatus.OK, HttpHeaders.of(ContentType.CSS), bytes);
    }

    public byte[] getBody() {
        return body;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void write(OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            writeResponseStatus(dos);
            writeHeader(dos);
            writeBody(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseStatus(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpStatus.toString());
        dos.writeBytes(System.lineSeparator());
    }

    private void writeHeader(DataOutputStream dos) throws IOException {
        for (HeaderResponse headerResponse : httpHeaders.toList()) {
            dos.writeBytes(headerResponse.keyValue());
        }
        dos.writeBytes(System.lineSeparator());
    }

    private void writeBody(DataOutputStream dos) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}

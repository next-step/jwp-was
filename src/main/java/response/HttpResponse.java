package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static response.ResponseType.*;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private String httpStatus;
    private byte[] body;
    private String host;
    private ResponseType responseType;

    public HttpResponse(byte[] bytes, ResponseType responseType) {
        this.httpStatus = "200 OK";
        this.body = bytes;
        this.responseType = responseType;
    }

    public HttpResponse(String httpStatus, byte[] bytes) {
        this.httpStatus = httpStatus;
        this.body = bytes;
        this.responseType = NOT_LOGIN;
    }

    public static HttpResponse of(byte[] bytes) {
        return new HttpResponse(bytes, NOT_LOGIN);
    }

    public static HttpResponse redirect(byte[] bytes, String host) {
        return new HttpResponse("302 FOUND", bytes);
    }

    public static HttpResponse loginFail(byte[] bytes) {
        return new HttpResponse(bytes, LOGIN_FAILED);
    }

    public static HttpResponse loginSuccess(byte[] bytes) {
        return new HttpResponse(bytes, LOGIN_SUCCESS);
    }

    public static HttpResponse css(byte[] bytes) {
        return new HttpResponse(bytes, CSS);
    }

    public int getBodyLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void write(OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeBytes("HTTP/1.1 "+ httpStatus + " \r\n");
            if (responseType == CSS) {
                dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            }
            else if (responseType != NOT_LOGIN) {
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Set-Cookie: logined=" + responseType.status() + "; Path=/\r\n");
            }
            else if ("200 OK".equals(httpStatus)) {
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Content-Length: " + getBodyLength() + "\r\n");
                dos.writeBytes("\r\n");
            }
            else if ("302 FOUND".equals(httpStatus)) {
                dos.writeBytes("Location: " + "http://localhost:8080/index.html" + "\r\n");
            }
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

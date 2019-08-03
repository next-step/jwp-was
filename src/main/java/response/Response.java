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
public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private String httpStatus;
    private byte[] body;
    private String host;
    private ResponseType responseType;

    public Response(byte[] bytes, ResponseType responseType) {
        this.httpStatus = "200 OK";
        this.body = bytes;
        this.responseType = responseType;
    }

    public Response(String httpStatus, byte[] bytes) {
        this.httpStatus = httpStatus;
        this.body = bytes;
        this.responseType = NOT_LOGIN;
    }

    public static Response of(byte[] bytes) {
        return new Response(bytes, NOT_LOGIN);
    }

    public static Response redirect(byte[] bytes, String host) {
        return new Response("302 FOUND", bytes);
    }

    public static Response loginFail(byte[] bytes) {
        return new Response(bytes, LOGIN_FAILED);
    }

    public static Response loginSuccess(byte[] bytes) {
        return new Response(bytes, LOGIN_SUCCESS);
    }

    public static Response css(byte[] bytes) {
        return new Response(bytes, CSS);
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

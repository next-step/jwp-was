package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import webserver.Header;
import webserver.Cookie;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public enum ResponseWriter {

    OK(HttpStatus.OK, (dos, response) -> {
        response200Header(dos, response);
        responseBody(dos, response);
    }),
    REDIRECT(HttpStatus.FOUND, ResponseWriter::response302Header),
    NOT_FOUND(HttpStatus.NOT_FOUND, ResponseWriter::response404Header);

    private static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_HTML_TEXT = "text/html";

    HttpStatus status;
    BiConsumer<DataOutputStream, HttpResponse> responseBiConsumer;

    ResponseWriter(HttpStatus status, BiConsumer<DataOutputStream, HttpResponse> responseBiConsumer) {
        this.status = status;
        this.responseBiConsumer = responseBiConsumer;
    }

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);
    private static final String HOST = "http://localhost:8080/";

    public static void response302Header(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            cookieLine(dos, response);
            dos.writeBytes("Location: " + HOST + response.getLocationPath() + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response200Header(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            contentTypeLine(dos, response);
            cookieLine(dos, response);
            dos.writeBytes("Content-Length: " + response.getBodyLength() + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void response404Header(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes("HTTP/1.1 404 NOT Found \r\n");
            cookieLine(dos, response);
            dos.writeBytes("Content-Length: " + response.getBodyLength() + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void contentTypeLine(DataOutputStream dos, HttpResponse response) throws IOException {
        Header header = response.getHeader();
        String contentType = Optional.ofNullable(header.get(HEADER_CONTENT_TYPE_KEY))
                .orElse(CONTENT_TYPE_HTML_TEXT);

        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
    }

    public static void cookieLine(DataOutputStream dos, HttpResponse response) {
        Cookie cookie = response.getCookie();
        if (cookie == null) {
            return ;
        }

        String cookieFormat = "Set-Cookie: %s=%s; Path=/\r\n";
        cookie.keySet().forEach(key -> {
            try {
                dos.writeBytes(String.format(cookieFormat, key, cookie.getCookie(key)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void responseBody(DataOutputStream dos, HttpResponse response) {
        try {
            dos.write(response.getBody(), 0, response.getBodyLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static Optional<ResponseWriter> findResponseBodyWrite(HttpStatus status) {
        return Arrays.stream(ResponseWriter.values())
                .filter(value -> value.status == status)
                .findAny();
    }

    public void write(HttpResponse response, DataOutputStream dos) {
       this.responseBiConsumer.accept(dos, response);
    }
}

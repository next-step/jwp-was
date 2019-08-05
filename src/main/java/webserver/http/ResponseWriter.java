package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public enum ResponseWriter {
    OK(HttpStatus.OK, (response, dos) -> {
        response200Header(response, dos);
        responseBody(response, dos);
    }),
    REDIRECT(HttpStatus.REDIRECT, ResponseWriter::response302Header);

    private static final Logger logger = LoggerFactory.getLogger(HttpStatus.class);
    private static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_HTML_TEXT = "text/html";

    private HttpStatus httpStatus;
    private BiConsumer<HttpResponse, DataOutputStream> responseConsumer;

    ResponseWriter(HttpStatus httpStatus, BiConsumer<HttpResponse, DataOutputStream> responseConsumer) {
        this.httpStatus = httpStatus;
        this.responseConsumer = responseConsumer;
    }

    private static void writeCookieHeaderLine(HttpResponse response, DataOutputStream dos) {
        Cookie cookie = response.getCookie();
        if (cookie.isEmpty()) return;

        String setCookieFormat = "Set-Cookie: %s=%s; Path=/\r\n";
        cookie.keySet().forEach(key -> {
            try {
                dos.writeBytes(String.format(setCookieFormat, key, cookie.get(key)));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
    }

    private static void response200Header(HttpResponse response, DataOutputStream dos) {
        byte[] body = response.getBody();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            writeContentTypeHeaderLine(response, dos);
            writeCookieHeaderLine(response, dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeContentTypeHeaderLine(HttpResponse response, DataOutputStream dos) throws IOException {
        HttpHeaders httpHeaders = response.getHttpHeaders();
        String contentType = Optional.ofNullable(httpHeaders.get(HEADER_CONTENT_TYPE_KEY))
                .orElse(CONTENT_TYPE_HTML_TEXT);

        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
    }

    private static void responseBody(HttpResponse response, DataOutputStream dos) {
        byte[] body = response.getBody();
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void response302Header(HttpResponse response, DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + response.getRedirectPath() + "\r\n");
            writeCookieHeaderLine(response, dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static Optional<ResponseWriter> valueByHttpStatus(HttpStatus httpStatus) {
        return Arrays.stream(ResponseWriter.values())
                .filter(v -> v.httpStatus.equals(httpStatus))
                .findAny();
    }

    public void write(HttpResponse httpResponse, DataOutputStream dos) {
        this.responseConsumer.accept(httpResponse, dos);
    }
}

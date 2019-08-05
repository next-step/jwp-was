package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.function.BiConsumer;

public enum HttpStatus {
    OK(200, "OK", (response, dos) -> {
        response200Header(response, dos);
        responseBody(response, dos);
    }),
    REDIRECT(302, "Found", HttpStatus::response302Header);

    private static final Logger logger = LoggerFactory.getLogger(HttpStatus.class);
    private static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_HTML_TEXT = "text/html";

    private int code;
    private String message;
    private BiConsumer<HttpResponse, DataOutputStream> responseConsumer;;


    HttpStatus(int code, String message, BiConsumer<HttpResponse, DataOutputStream> responseConsumer) {
        this.code = code;
        this.message = message;
        this.responseConsumer = responseConsumer;
    }

    public BiConsumer<HttpResponse, DataOutputStream> getResponseConsumer() {
        return responseConsumer;
    }

    private static void setCookies(HttpResponse response, DataOutputStream dos) {
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
            setContentType(response, dos);
            setCookies(response, dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void setContentType(HttpResponse response, DataOutputStream dos) throws IOException {
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
            setCookies(response, dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

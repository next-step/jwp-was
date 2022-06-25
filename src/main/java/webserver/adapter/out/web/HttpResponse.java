package webserver.adapter.out.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {

    private static final String HTTP_200 = "HTTP/1.1 200 OK \r\n";
    private static final String HTTP_302 = "HTTP/1.1 302 Found \r\n";

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;

    private final Map<String, String> headers;

    public HttpResponse(DataOutputStream dos) {
        this.dos = new DataOutputStream(dos);
        this.headers = new HashMap<>();
    }

    public void responseRedirect(String redirectUrl) throws IOException {
        redirect(redirectUrl);
        responseBody(new byte[]{});

    }

    public void responseCookies(String redirectUrl, boolean logined) throws IOException {
        redirect(redirectUrl);
        addHeader("Set-Cookie", "logined=" + logined + "; Path=/");
        responseBody(new byte[]{});
    }

    public void responseBody(String body) throws IOException {
        dos.writeBytes(HTTP_200);
        addHeader("Content-Type", "text/html;charset=utf-8");
        responseBody(body.getBytes(StandardCharsets.UTF_8));
    }

    public void responseForward(String path) throws IOException, URISyntaxException {
        dos.writeBytes(HTTP_200);

        if (path.endsWith(".html")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + path);
            addHeader("Content-Type", "text/html;charset=utf-8");
            addHeader("Content-Length", String.valueOf(body.length));
            responseBody(body);
        } else if (path.endsWith(".css")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + path);
            addHeader("Content-Type", "text/css;charset=utf-8");
            responseBody(body);
        } else if (path.endsWith(".js")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + path);
            addHeader("Content-Type", "text/javascript;charset=utf-8");
            responseBody(body);
        } else if (path.contains("/fonts/")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + path);
            addHeader("Content-Type", "font/" + path.substring(path.lastIndexOf(".") + 1) + ";charset=utf-8");
            responseBody(body);
        } else if (path.equals("/favicon.ico")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + path);
            addHeader("image/x-icon;charset=utf-8", null);
            responseBody(body);
        }
    }

    private void redirect(String redirectUrl) throws IOException {
        dos.writeBytes(HTTP_302);
        addHeader("Location", redirectUrl);
    }

    private void responseBody(byte[] body) throws IOException {
        setHeader();
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }

    private void addHeader(String key, String value) {
        headers.put(key, value);
    }

    private void setHeader() {
        headers.forEach((key, value) -> {
            try {
                if (Objects.isNull(value)) {
                    dos.writeBytes(key + "\r\n");
                }
                dos.writeBytes(key + ": " + value + "\r\n");
            } catch (IOException e) {
                logger.error("{}", e.getMessage(), e);
            }
        });
    }
}

package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream out;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void forward(String path, Object model) throws IOException, URISyntaxException {
        byte[] body;

        if (isStaticResourcePath(path)) {
            body = FileIoUtils.loadFileFromClasspath("./static/" + path);
        } else {
            String view = HandlebarsUtils.getView(path, model);
            body = view.getBytes(StandardCharsets.UTF_8);
            addHeader("Content-Type", "text/html;charset=utf-8");
        }

        addHeader("Content-Length", String.valueOf(body.length));
        response200Header();
        responseBody(body);
    }

    private boolean isStaticResourcePath(String path) {
        return Stream.of(".js", ".css", ".woff", ".ttf", ".ico")
            .anyMatch(path::endsWith);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        forward(path, null);
    }

    public void sendRedirect(String url) {
        try {
            out.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            out.writeBytes(String.format("Location: %s%n", url));
            out.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header() {
        try {
            out.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            out.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        headers.forEach((key, value) -> {
            try {
                out.writeBytes(String.format("%s: %s%n", key, value));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
    }

    private void responseBody(byte[] body) {
        try {
            out.write(body, 0, body.length);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

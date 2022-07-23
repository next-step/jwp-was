package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream out;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    public void forward(String viewName, Object model) {
        String view = HandlebarsUtils.getView(viewName, model);
        byte[] body = view.getBytes(StandardCharsets.UTF_8);

        headers.put("Content-Length", String.valueOf(body.length));
        headers.put("Content-Type", "text/html;charset=utf-8");

        response200Header();
        responseBody(body);
    }

    public void forward(String viewName) {
        forward(viewName, null);
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

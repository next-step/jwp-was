package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import utils.HandlebarsUtils;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final DataOutputStream out;
    private final Map<String, String> headers = new HashMap<>();
    private final StatusLine statusLine;
    private final Cookies cookies;

    public HttpResponse(OutputStream out) {
        this.out = new DataOutputStream(out);
        this.statusLine = new StatusLine();
        this.cookies = new Cookies();
    }

    public void setStatus(HttpStatus status) {
        statusLine.setStatus(status);
    }

    public HttpStatus getStatus() {
        return statusLine.getStatus();
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        forward(path, null);
    }

    public void forward(String path, Object model) throws IOException, URISyntaxException {
        byte[] body = getBody(path, model);
        addHeader("Content-Length", String.valueOf(body.length));
        response200Header();
        responseBody(body);
    }

    private byte[] getBody(String path, Object model) throws IOException, URISyntaxException {
        if (isStaticResourcePath(path)) {
            addHeader("Content-Type", getContentType(path));
            return FileIoUtils.loadFileFromClasspath(getResourcePath(path));
        }

        addHeader("Content-Type", ContentType.TEXT_HTML.getValue());
        String view = HandlebarsUtils.getView(path, model);
        return view.getBytes(StandardCharsets.UTF_8);
    }

    private boolean isStaticResourcePath(String path) {
        return Arrays.stream(ContentType.values())
            .anyMatch(type -> path.endsWith(type.getExtension()));
    }

    private String getContentType(String path) {
        return ContentType.from(path).getValue();
    }

    private String getResourcePath(String path) {
        if (path.endsWith(".html")) {
            return "./templates/" + path;
        }
        return "./static/" + path;
    }

    public void sendRedirect(String url) {
        try {
            addHeader("Location", url);
            processStatusLine(HttpStatus.FOUND);
            processHeaders();
            out.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header() {
        try {
            processStatusLine(HttpStatus.OK);
            processHeaders();
            out.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processStatusLine(HttpStatus status) throws IOException {
        setStatus(status);
        out.writeBytes(statusLine + LINE_SEPARATOR);
    }

    private void processHeaders() throws IOException {
        List<String> cookies = this.cookies.getCookies();
        if (!cookies.isEmpty()) {
            addHeader("Set-Cookie", String.join(";", cookies));
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            out.writeBytes(String.format("%s: %s%n", entry.getKey(), entry.getValue()));
        }
    }

    private void responseBody(byte[] body) {
        try {
            out.write(body, 0, body.length);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void addCookie(String name, String value) {
        cookies.add(name, value);
    }
}

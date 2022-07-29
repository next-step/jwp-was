package webserver.http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private final HttpProtocol httpProtocol;
    private final Map<String, Object> headers = new HashMap<>();

    public HttpResponse(OutputStream out, HttpRequest httpRequest) {
        this.dos = new DataOutputStream(out);
        this.httpProtocol = httpRequest.getHttpProtocol();
    }

    public void addHeader(String key, Object value) {
        headers.put(key, value);
    }

    public void forward(String path) {
        final byte[] payload = getPayload(path);
        writeForward(payload);
    }

    public void forward(String path, Object context) {
        final byte[] payload = getPayload(path, context);
        writeForward(payload);
    }

    private void writeForward(byte[] payload) {
        addHeader("Content-Length", payload.length);
        write(payload, HttpStatus.OK);
    }

    private byte[] getPayload(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates" + path);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] getPayload(String path, Object context) {
        try {
            Template template = compileTemplate(path);
            final String html = template.apply(context);
            return html.getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void redirect(String path) {
        addHeader("Location", path);
        write(HttpStatus.FOUND);
    }

    private Template compileTemplate(String path) {
        Template template;
        try {
            template = getHandlebars().compile(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return template;
    }

    private Handlebars getHandlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        return new Handlebars(loader);
    }

    private void write(HttpStatus httpStatus) {
        try {
            writeResponseLine(httpStatus);
            writeHeaders();
            dos.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void write(byte[] payload, HttpStatus httpStatus) {
        try {
            writeResponseLine(httpStatus);
            writeHeaders();
            writePayload(payload);
            dos.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void writeResponseLine(HttpStatus httpStatus) throws IOException {
        dos.writeBytes(String.format("%s/%s %d %s\r\n", httpProtocol.getProtocol(), httpProtocol.getVersion(), httpStatus.getCode(), httpStatus.name()));
    }

    private void writeHeaders() {
        headers.forEach((key, value) -> {
            try {
                dos.writeBytes(String.format("%s: %s\r\n", key, value));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private void writePayload(byte[] payload) throws IOException {
        dos.writeBytes("Content-Length: " + payload.length + "\r\n");
        dos.writeBytes("\r\n");
        dos.write(payload, 0, payload.length);
    }
}

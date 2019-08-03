package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final OutputStream out;

    private HttpResponse(final OutputStream out) {
        this.out = out;
    }

    public static HttpResponse of(final OutputStream out) {
        return new HttpResponse(out);
    }

    public void notFound() {
        try (final DataOutputStream responseWriter = new DataOutputStream(out)) {
            responseWriter.writeBytes("HTTP/1.1 404 Not Found \r\n\r\n");
        } catch (final IOException e) {
            // TODO: error handling??
            e.printStackTrace();
        }
    }

    public void ok(final byte[] body) {
        try (final DataOutputStream responseWriter = new DataOutputStream(out)) {
            responseWriter.writeBytes("HTTP/1.1 200 OK \r\n");
            responseWriter.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            responseWriter.writeBytes("Content-Length: " + body.length + "\r\n");
            responseWriter.writeBytes("\r\n");
            responseWriter.write(body, 0, body.length);
            responseWriter.flush();
        } catch (final IOException e) {
            // TODO: error handling??
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        out.close();
    }
}

package webserver;

import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse implements AutoCloseable {

    private final OutputStream out;

    private final HttpHeaders headers = HttpHeaders.empty();

    private HttpResponse(final OutputStream out) {
        this.out = out;
    }

    public static HttpResponse of(final OutputStream out) {
        return new HttpResponse(out);
    }

    public void addHeader(final String key,
                          final String value) {
        headers.add(key, value);
    }

    public void notFound() {
        try (final DataOutputStream responseWriter = new DataOutputStream(out)) {
            responseWriter.writeBytes("HTTP/1.1 404 Not Found \r\n\r\n");
            responseWriter.flush();
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
            writeHeaders(responseWriter);
            responseWriter.writeBytes("\r\n");
            responseWriter.write(body, 0, body.length);
            responseWriter.flush();
        } catch (final IOException e) {
            // TODO: error handling??
            e.printStackTrace();
        }
    }

    public void redirect(final String redirectPath) {
        try (final DataOutputStream responseWriter = new DataOutputStream(out)) {
            responseWriter.writeBytes("HTTP/1.1 302 Found \r\n");
            writeHeaders(responseWriter);
            responseWriter.writeBytes("Location: " + redirectPath + "\r\n\r\n");
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

    private void writeHeaders(final DataOutputStream responseWriter) throws IOException {
        for (final HttpHeader header : headers.toList()) {
            responseWriter.writeBytes(header + "\r\n");
        }
    }
}

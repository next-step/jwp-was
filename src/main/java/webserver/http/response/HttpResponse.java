package webserver.http.response;

import webserver.http.HeaderKey;
import webserver.http.HttpStatus;
import webserver.http.header.HttpHeader;
import webserver.http.header.HttpHeaders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse implements Response {

    private final OutputStream out;
    private final HttpHeaders headers = HttpHeaders.empty();

    private ResponseLine responseLine;
    private byte[] body = {};

    private HttpResponse(final OutputStream out) {
        this.out = out;
    }

    public static HttpResponse of(final OutputStream out) {
        return new HttpResponse(out);
    }

    @Override
    public void addHeader(final HeaderKey key,
                          final String value) {
        addHeader(key.toString(), value);
    }

    @Override
    public void addHeader(final String key,
                          final String value) {
        headers.add(key, value);
    }

    @Override
    public void notFound() {
        responseLine = ResponseLine.of(HttpStatus.NOT_FOUND);
    }

    @Override
    public void ok(final String body) {
        ok(body.getBytes());
    }

    @Override
    public void ok(final byte[] body) {
        responseLine = ResponseLine.of(HttpStatus.OK);
        headers.setContentLength(body.length);

        this.body = body;
    }

    @Override
    public void sendRedirect(final String redirectPath) {
        responseLine = ResponseLine.of(HttpStatus.FOUND);
        headers.setLocation(redirectPath);
    }

    @Override
    public void close() throws Exception {
        try (out;
             final DataOutputStream responseWriter = new DataOutputStream(out)) {
            writeStatusLine(responseWriter);
            writeHeaders(responseWriter);
            writeBody(responseWriter);
            responseWriter.flush();
        }
    }

    private void writeStatusLine(final DataOutputStream responseWriter) throws IOException {
        responseWriter.writeBytes(responseLine.toString());
        newLine(responseWriter);
    }

    private void writeHeaders(final DataOutputStream responseWriter) throws IOException {
        for (final HttpHeader header : headers.toList()) {
            responseWriter.writeBytes(header.toString());
            newLine(responseWriter);
        }
    }

    private void writeBody(final DataOutputStream responseWriter) throws IOException {
        newLine(responseWriter);
        responseWriter.write(body);
    }

    private void newLine(final DataOutputStream responseWriter) throws IOException {
        responseWriter.writeBytes(System.lineSeparator());
    }
}

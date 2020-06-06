package webserver.writer;

import http.HttpResponse;

import java.io.OutputStream;

public interface HttpResponseWriter {
    void write(final OutputStream outputStream, final HttpResponse httpResponse);
}

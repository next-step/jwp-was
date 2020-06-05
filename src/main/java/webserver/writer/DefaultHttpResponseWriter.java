package webserver.writer;

import http.HttpResponse;

import java.io.OutputStream;

public class DefaultHttpResponseWriter implements HttpResponseWriter {

    @Override
    public void write(final OutputStream outputStream, final HttpResponse httpResponse) {
        httpResponse.writeResponse(outputStream);
    }
}

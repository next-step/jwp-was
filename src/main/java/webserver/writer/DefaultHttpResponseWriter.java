package webserver.writer;

import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

public class DefaultHttpResponseWriter implements HttpResponseWriter {

    @Override
    public void write(final OutputStream outputStream, final HttpResponse httpResponse) {
        httpResponse.writeResponse(outputStream);
    }
}

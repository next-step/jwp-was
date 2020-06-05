package http.responsetemplate;

import http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class Redirect extends ResponseTemplate {

    @Override
    protected void writeHeaderLine(final HttpResponse httpResponse,
                                   final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
    }

    @Override
    protected void writeBody(final HttpResponse httpResponse,
                             final DataOutputStream dataOutputStream) throws IOException {
        // do nothing
    }

    @Override
    protected void writeHeader(final HttpResponse httpResponse,
                               final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes("Location: " + httpResponse.getLocation() + "\r\n");
    }
}

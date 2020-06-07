package http.responsetemplate;

import http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class Ok extends ResponseTemplate {

    @Override
    protected void writeHeaderLine(final HttpResponse httpResponse,
                                   final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
    }

    @Override
    protected void writeBody(final HttpResponse httpResponse,
                             final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(httpResponse.getBody(), 0, httpResponse.getBodyLength());
    }

    @Override
    protected void writeHeader(final HttpResponse httpResponse,
                               final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes("Content-Type: " + httpResponse.getContentType().getContentTypeStr() + ";charset=utf-8\r\n");
        dataOutputStream.writeBytes("Content-Length: " + httpResponse.getBodyLength() + " \r\n");
        dataOutputStream.writeBytes("\r\n");
    }
}

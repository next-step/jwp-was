package http.responsetemplate;

import http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class NotFound extends ResponseTemplate {
    @Override
    protected void writeHeaderLine(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes("HTTP/1.1 404 Not Found \r\n");
    }

    @Override
    protected void writeBody(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws IOException {
        //do nothing
    }

    @Override
    protected void writeHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws IOException {
        //do nothing
    }
}

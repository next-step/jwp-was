package http.responsetemplate;

import http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class Ok extends ResponseTemplate {

    @Override
    protected void writeResponseHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void writeBody(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.write(httpResponse.getBody(), 0, httpResponse.getBodyLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void writeHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeBytes("Content-Type: " + httpResponse.getContentType().getContentTypeStr() + ";charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + httpResponse.getBodyLength() + " \r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

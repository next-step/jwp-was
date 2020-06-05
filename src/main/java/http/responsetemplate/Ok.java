package http.responsetemplate;

import http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class Ok implements ResponseTemplate {

    @Override
    public void write(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: " + httpResponse.getContentType().getContentTypeStr() + ";charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + httpResponse.getBodyLength() + "\r\n");
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.write(httpResponse.getBody(), 0, httpResponse.getBodyLength());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

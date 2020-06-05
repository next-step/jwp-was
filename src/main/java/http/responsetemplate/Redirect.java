package http.responsetemplate;

import http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class Redirect extends ResponseTemplate {

    @Override
    protected void writeHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
            dataOutputStream.writeBytes("Location: " + httpResponse.getLocation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void writeBody(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        // do nothing
    }
}

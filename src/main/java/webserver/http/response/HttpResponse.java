package webserver.http.response;

import webserver.view.View;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse implements Response {

    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    @Override
    public void writeHeader(String contentType, int contentLength) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + contentLength + "\r\n");
        dos.writeBytes("\r\n");
    }

    @Override
    public void redirect(String location, boolean logined) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
        dos.writeBytes("Set-Cookie: logined=" + logined + "; Path=/ \r\n");
        dos.writeBytes("Location: " + location + " \r\n");
        dos.writeBytes("\r\n");
    }

    @Override
    public void writeBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    @Override
    public void writeBody(View view) throws IOException {
        dos.write(view.getBody(), 0, view.getLength());
        dos.flush();
    }
}

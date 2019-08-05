package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void response200Header(int lengthOfBodyContent, String contentType) throws IOException {
        dos.writeBytes("HTTP/1.1 200 \r\n");
        dos.writeBytes("Content-Type: " + contentType + "charset=utf-8 \r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + " \r\n");
        dos.writeBytes("\r\n");
    }

    public void response302Header(String location, boolean logined) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: " + location + " \r\n");
        dos.writeBytes("Set-Cookie: logined=" + logined + "; Path=/ \r\n");
        dos.writeBytes("\r\n");
    }

    public void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}

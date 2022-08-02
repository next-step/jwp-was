package webserver.request.domain.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    private DataOutputStream dos;

    public HttpResponse(OutputStream outputStream) {
        dos = new DataOutputStream(outputStream);
    }

    public void forward(String path) throws IOException {
        int lengthOfBodyContent = path.length();

        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");

        responseBody(path.getBytes());
    }

    public void forwardCSS(String path) throws IOException {
        int lengthOfBodyContent = path.length();

        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/css\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");

        responseBody(path.getBytes());
    }

    public void redirect(String path) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: " + path + "\r\n");
        dos.writeBytes("\r\n");
    }

    public void loginRedirect(String path, String setCookie) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: " + path + "\r\n");
        dos.writeBytes("Set-Cookie: " + setCookie + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}

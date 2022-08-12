package webserver.response;

import webserver.ContentType;
import webserver.request.Header;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    private static final String CRLF = "\r\n";

//    private StatusLine statusLine;
//    private Header header;
//    private ResponseBody responseBody;

    private DataOutputStream dos;

//    public HttpResponse(StatusLine statusLine, Header header, ResponseBody responseBody) {
//        this.statusLine = statusLine;
//        this.header = header;
//        this.responseBody = responseBody;
//    }

    public HttpResponse(OutputStream outputStream) {
        dos = new DataOutputStream(outputStream);
    }

    public void forward(String path, String mediaType) throws IOException {
        int lengthOfBodyContent = path.length();

        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: " + mediaType + "\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");

        responseBody(path.getBytes());
    }

//    public void redirect(String path) throws IOException {
//        dos.writeBytes("HTTP/1.1 302 Found \r\n");
//        dos.writeBytes("Location: " + path + "\r\n");
//        dos.writeBytes("\r\n");
//    }

    public void redirect(String path, String setCookie) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: " + path + "\r\n");
        if (setCookie != null) {
            dos.writeBytes("Set-Cookie: " + setCookie + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}

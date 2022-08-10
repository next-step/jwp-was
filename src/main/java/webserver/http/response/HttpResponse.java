package webserver.http.response;

import utils.FileIoUtils;
import webserver.http.StaticContentType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    private final DataOutputStream dos;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void forward(final String pathValue) {
        StaticContentType contentType = StaticContentType.fromRequestPath(pathValue);

        byte[] bytes = FileIoUtils.loadFileFromClasspath(contentType.getPath() + pathValue);

        response200Header(bytes.length);
        responseBody(bytes);
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

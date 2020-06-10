package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response {

    private static final String BASIC_PROTOCOL = "HTTP/1.1";
    private static final String BASIC_CONTENT_KEY = "Content-Type:";
    private static final String BASIC_REQUEST = "%s %s \r\n";
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private final DataOutputStream dataOutputStream;

    public Response(final DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void responseHeaderByCss() {
        try {
            responseOK();
            dataOutputStream.writeBytes("Host: localhost:8080 \r\n");
            dataOutputStream.writeBytes("Accept: text/css,*/*;q=0.1 \r\n");
            dataOutputStream.writeBytes("Connection: keep-alive \r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void response302Header(String location) {
        try {
            responseFound();
            dataOutputStream.writeBytes("Location: " + location + " \r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void responseHeaderByLoginSuccess() {
        try {
            responseFound();
            responseContentTypeHtml();
            dataOutputStream.writeBytes("Set-Cookie: logined=true; Path=/ \r\n");
            dataOutputStream.writeBytes("Location: /index.html \r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302HeaderByLoginFail(String location) {
        try {
            responseFound();
            dataOutputStream.writeBytes("Set-Cookie: logined=false; Path=/ \r\n");
            dataOutputStream.writeBytes("Location: " + location + " \r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            responseOK();
            responseContentTypeHtml();
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseOK() throws IOException {
        dataOutputStream.writeBytes(String.format(BASIC_REQUEST, BASIC_PROTOCOL, ResponseHttpStatus.Found.toString()));
    }

    private void responseFound() throws IOException {
        dataOutputStream.writeBytes(String.format(BASIC_REQUEST, BASIC_PROTOCOL, ResponseHttpStatus.Found.toString()));
    }

    private void responseContentTypeHtml() throws IOException {
        dataOutputStream.writeBytes(String.format(BASIC_REQUEST, BASIC_CONTENT_KEY, ResponseContentType.HTML.toString()));
    }

    public void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

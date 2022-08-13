package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import webserver.http.HttpContentType;
import webserver.http.response.body.HttpResponseBody;
import webserver.http.response.headers.HttpResponseHeader;
import webserver.http.response.start_line.HttpResponseStatusLine;
import webserver.http.response.start_line.StatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;

    private HttpResponseStatusLine responseStatusLine;
    private HttpResponseHeader responseHeader;
    private HttpResponseBody responseBody;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void forward(final String pathValue) {
        HttpContentType contentType = HttpContentType.fromRequestPath(pathValue);

        byte[] bytes = FileIoUtils.loadFileFromClasspath(contentType.getPath() + pathValue);

        this.responseStatusLine = new HttpResponseStatusLine("HTTP/1.1 " + HttpStatus.OK.value() + " " + HttpStatus.OK.name());
        this.responseHeader = HttpResponseHeader.fromContent(contentType.getContentType(), bytes.length);
        this.responseBody = new HttpResponseBody(bytes);

        responseHeader();
        responseBody();
    }

    public void redirect(final StatusCode statusCode, final String path) {
        HttpContentType contentType = HttpContentType.fromRequestPath(path);
//        byte[] bytes = FileIoUtils.loadFileFromClasspath(contentType.getPath() + path);

        this.responseStatusLine = new HttpResponseStatusLine("HTTP/1.1 " + statusCode.getValue() + " " + statusCode.name());
        this.responseHeader = HttpResponseHeader.fromLocation(path);
//        this.responseBody = new HttpResponseBody(bytes);
        this.responseBody = HttpResponseBody.empty();

        responseHeader();
        responseBody();
    }

    private void responseHeader() {
        try {
            dos.writeBytes(responseStatusLine.getStatusLine());
            dos.writeBytes("\r\n");

            Map<String, String> headers = responseHeader.getHeaders();

            for (String headerKey : headers.keySet()) {
                String headerValue = headers.get(headerKey);
                dos.writeBytes(headerKey + ": " + headerValue + "\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void responseBody() {
        try {
            dos.write(responseBody.getBody(), 0, responseBody.getBody().length);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

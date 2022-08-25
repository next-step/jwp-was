package webserver.http.response;

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

    private final DataOutputStream dos;

    private HttpResponseStatusLine responseStatusLine;
    private HttpResponseHeader responseHeader;
    private HttpResponseBody responseBody;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.responseStatusLine = HttpResponseStatusLine.empty();
        this.responseHeader = HttpResponseHeader.empty();
        this.responseBody = HttpResponseBody.empty();
    }

    public void forward(final String pathValue) {
        HttpContentType contentType = HttpContentType.fromRequestPath(pathValue);

        byte[] bytes = FileIoUtils.loadFileFromClasspath(contentType.getPath() + pathValue);

        responseStatusLine.addStatusLine("HTTP/1.1 " + HttpStatus.OK.value() + " " + HttpStatus.OK.name());
        responseHeader.addHeader("content-type", contentType.getContentType());
        responseHeader.addHeader("content-length", String.valueOf(bytes.length));
        responseBody.addBody(bytes);

        responseHeader();
        responseBody();
    }

    public void redirect(final StatusCode statusCode, final String path) {
        responseStatusLine.addStatusLine("HTTP/1.1 " + statusCode.getValue() + " " + statusCode.name());
        responseHeader.addHeader("location", path);

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

    public void addHeader(String key, String value) {
        responseHeader.addHeader(key, value);
    }
}

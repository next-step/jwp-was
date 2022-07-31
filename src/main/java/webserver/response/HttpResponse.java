package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.domain.ContentType;
import webserver.domain.HttpHeader;
import webserver.enums.Protocol;
import webserver.enums.StatusCode;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private StatusLine statusLine;
    private HttpResponseHeader header;
    private HttpResponseBody body;
    private OutputStream outputStream;

    public HttpResponse() {
        this(new StatusLine(Protocol.HTTP_1_1, StatusCode.OK), HttpResponseHeader.createEmpty(), HttpResponseBody.createEmpty());
    }

    public HttpResponse(StatusLine statusLine) {
        this(statusLine, HttpResponseHeader.createEmpty(), HttpResponseBody.createEmpty());
    }

    private HttpResponse(StatusLine statusLine, HttpResponseHeader header, HttpResponseBody body) {
        this.statusLine = statusLine;
        this.header = header;
        this.body = body;
    }

    public HttpResponse(OutputStream outputStream) {
        this();
        this.outputStream = outputStream;
    }

    public static HttpResponse createBadRequest() {
        return new HttpResponse(new StatusLine(Protocol.HTTP_1_1, StatusCode.BAD_REQUEST));
    }

    public void forward(String resourceUri) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(resourceUri);
            forwardBody(body);
        } catch (IOException e) {
            notFound();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardBody(String page) {
        byte[] body = page.getBytes(StandardCharsets.UTF_8);
        forwardBody(body);
    }

    private void forwardBody(byte[] body) {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.OK);
        this.body = HttpResponseBody.of(body);

        writeResponse();
    }

    public void sendRedirect(String redirection) {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.FOUND);
        header.putHeader(HttpHeader.LOCATION, redirection);
        header.putHeader(HttpHeader.CONTENT_TYPE, ContentType.HTML.type());

        writeResponse();
    }

    public void notFound() {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.NOT_FOUND);
        String notFoundGoogle = "404. That’s an error.\n"
            + "The requested URL was not found on this server. That’s all we know.";
        this.body = HttpResponseBody.of(notFoundGoogle.getBytes(StandardCharsets.UTF_8));

        writeResponse();
    }

    public void addHeader(String key, String value) {
        header.putHeader(key, value);
    }

    public String statusLine() {
        return this.statusLine.toString();
    }

    public HttpResponseHeader getHeader() {
        return header;
    }

    private byte[] getBody() {
        return body.get();
    }

    public void setBody(byte[] body) {
        this.body = HttpResponseBody.of(body);
    }

    public StatusCode getStatus() {
        return this.statusLine.getStatusCode();
    }


    private void writeResponse() {
        if (outputStream == null) {
            return;
        }

        DataOutputStream dos = new DataOutputStream(outputStream);
        header.putHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length()));
        try {
            responseStatusLine(dos, statusLine());
            responseHeader(dos, getHeader());
            responseBody(dos, getBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseStatusLine(DataOutputStream dos, String statusLine) throws IOException {
        dos.writeBytes(statusLine + " \r\n");
        logger.debug(statusLine);
    }

    private void responseHeader(DataOutputStream dos, HttpResponseHeader responseHeader) throws IOException {
        for (String key : responseHeader.keySet()) {
            dos.writeBytes(key + ": " + responseHeader.getHeader(key) + "\r\n");
            logger.debug(key + ": " + responseHeader.getHeader(key));
        }
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}

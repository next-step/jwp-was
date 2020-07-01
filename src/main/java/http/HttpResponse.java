package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String TEMPLATE_PREFIX = "./templates/";
    private static final String CARRIAGE_RETURN = "\r\n";

    private final OutputStream out;
    private final DataOutputStream dos;
    private final PrintWriter printWriter;

    private ResponseLine responseLine;
    private ResponseHeaders responseHeaders = new ResponseHeaders();
    private byte[] responseBody;

    public HttpResponse(final OutputStream out) {
        this.out = out;
        this.dos = new DataOutputStream(out);
        this.printWriter = new PrintWriter(out);
    }

    public void buildResponseLine(HttpStatus httpStatus) {
        this.responseLine = new ResponseLine(httpStatus.getCodeValue(httpStatus), httpStatus.getValue());
    }

    public void print() {
        try {
            System.out.println("@: " + responseHeaders.getContentType());
            dos.writeBytes(responseLine.getResponseLine() + CARRIAGE_RETURN);
            dos.writeBytes("Content-Type: " + responseHeaders.getContentType() + "; charset=utf-8" + CARRIAGE_RETURN);
            if (!"".equals(responseBody) && responseBody != null) {
                dos.write(responseBody, 0, responseBody.length);
            }
            dos.flush();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getTemplateFile(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath("./templates/" + path);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \n");
            dos.writeBytes("Location: " + path + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void setContentType(final String contentType) {
        System.out.println("contentType: " + contentType);
        responseHeaders.addHeader(HttpHeader.CONTENT_TYPE, Arrays.asList(contentType));
    }

    public void setContentLength(String contentLength) {
        responseHeaders.addHeader(HttpHeader.CONTENT_LENGTH, Arrays.asList(contentLength));
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(final String requestPath) throws IOException, URISyntaxException {
        responseBody = FileIoUtils.loadFileFromClasspath(TEMPLATE_PREFIX + requestPath);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "responseLine=" + responseLine +
                ", responseHeaders=" + responseHeaders.getResponseHeaders() +
                ", responseBody=" + responseBody +
                '}';
    }

}

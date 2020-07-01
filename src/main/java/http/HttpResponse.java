package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Arrays;

import static http.HttpHeader.SET_COOKIE;

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
            dos.writeBytes(responseLine.getResponseLine() + CARRIAGE_RETURN);
            dos.writeBytes(responseHeaders.getResponseHeaders() + CARRIAGE_RETURN);
            if (!"".equals(responseBody) && responseBody != null) {
                dos.write(responseBody, 0, responseBody.length);
            }
            dos.flush();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void setCookie(final boolean cookie) {
        if (cookie) {
            responseHeaders.addHeader(SET_COOKIE, Arrays.asList("logined=true; Path=/"));
            return;
        }
        responseHeaders.addHeader(SET_COOKIE, Arrays.asList("logined=false;"));
    }

    public void setContentType(final String contentType) {
        System.out.println("contentType: " + contentType);
        responseHeaders.addHeader(HttpHeader.CONTENT_TYPE, Arrays.asList(contentType));
    }

    public void setContentLength(String contentLength) {
        responseHeaders.addHeader(HttpHeader.CONTENT_LENGTH, Arrays.asList(contentLength));
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

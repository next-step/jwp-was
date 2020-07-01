package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import static http.HttpHeader.SET_COOKIE;
import static java.util.Arrays.asList;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
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
            dos.write(responseBody, 0, responseBody.length);
            dos.flush();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void setCookie(final boolean cookie) {
        if (cookie) {
            responseHeaders.addHeader(SET_COOKIE, asList("logined=true; Path=/"));
            return;
        }
        responseHeaders.addHeader(SET_COOKIE, asList("logined=false;"));
    }

    public void setContentType(final String contentType) {
        responseHeaders.addHeader(HttpHeader.CONTENT_TYPE, asList(contentType));
    }

    public void setCharset(String charset) {
        String contentType = responseHeaders.getContentType();
        responseHeaders.addHeader(HttpHeader.CONTENT_TYPE, asList(contentType + ";charset=" + charset));
    }

    public void setResponseBody(final String requestPath) {
        try {
            responseBody = loadFileFromClasspath(requestPath);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private byte[] loadFileFromClasspath(final String requestPath) throws IOException, URISyntaxException {
        String prefix = "./templates/";
        if (requestPath.endsWith(".ico")) {
            prefix = "./templates/";
            responseHeaders.addHeader(HttpHeader.CONTENT_TYPE, asList("image/x-icon"));
        } else if (requestPath.endsWith(".html")) {
            prefix = "./templates/";
        } else if (requestPath.endsWith(".css")) {
            prefix = "./static/";
        } else if (requestPath.endsWith(".js")) {
            prefix = "./static/";
        } else if (requestPath.endsWith(".ttf") || requestPath.endsWith(".svg") ||
                requestPath.endsWith(".eot") || requestPath.endsWith(".woff") ||
                requestPath.endsWith(".ttf2")) {
            prefix = "./static/";
            responseHeaders.addHeader(HttpHeader.CONTENT_TYPE, asList("font/woff"));
        }
        byte[] bytes = FileIoUtils.loadFileFromClasspath(prefix + requestPath);
        return bytes;
    }

    public void setTemplate(final String template) {
        this.responseBody = template.getBytes();
    }

    public String getResponseBody() {
        return new String(responseBody);
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

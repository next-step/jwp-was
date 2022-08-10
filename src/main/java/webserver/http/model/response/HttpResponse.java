package webserver.http.model.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private StatusLine statusLine;
    private ResponseHeaders responseHeaders;

    private DataOutputStream dataOutputStream;

    public HttpResponse(OutputStream outputStream) {
        dataOutputStream = new DataOutputStream(outputStream);
        responseHeaders = new ResponseHeaders();
    }

    public void addHeader(String key, String value) {
        responseHeaders.getResponseHeaderMap().put(key, value);
    }

    public void forward(String forwardPath) {
        statusLine = new StatusLine(StatusCode.OK);
        responseHeaders.getResponseHeaderMap().put("Location", forwardPath);
        processHeaders();
    }

    public void forwardBody(String str) {
    }

    public void response200Header(String str) {
    }

    public void responseBody(byte[] bytes) {
    }

    public void sendRedirect(String redirectPath) {
        statusLine = new StatusLine(StatusCode.FOUND);
        responseHeaders.getResponseHeaderMap().put("Location", redirectPath);
        processHeaders();
    }

    public void processHeaders() {
        try {
            dataOutputStream.writeBytes(statusLine.statusLineText());
            for (String key : responseHeaders.getResponseHeaderMap().keySet()) {
                dataOutputStream.writeBytes(key + ": " + responseHeaders.getResponseHeaderMap().get(key) + " \r\n");
            }
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

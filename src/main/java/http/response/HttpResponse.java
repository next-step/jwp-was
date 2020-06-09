package http.response;

import dto.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewHelper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static http.response.ResponseStatus.FOUND;
import static http.response.ResponseStatus.OK;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String STATUS_PREFIX = "HTTP/1.1";
    private static final String HEADER_END_STRING = "\r\n";

    private ResponseStatus status;
    private ResponseHeader header;
    private ResponseBody body;
    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void sendRedirect(String redirectUrl) {
        this.status = FOUND;
        this.header = new ResponseHeader();
        header.addLocation(redirectUrl);
        this.body = ResponseBody.emptyBody();
        this.write();
    }

    public void forward(String returnFile) {
        this.status = OK;
        this.header = new ResponseHeader();
        this.body = new ResponseBody(ViewHelper.readFile(returnFile));
        header.addHeader("Content-Type", "text/html;charset=utf-8");
        header.addHeader("Content-Length", String.valueOf(body.getLength()));
    }

    public void returnHandlebar(String location, Users users) {
        this.status = OK;
        this.header = new ResponseHeader();
        this.body = new ResponseBody(ViewHelper.readHandlebar(location, users));
        header.addHeader("Content-Type", "text/html;charset=utf-8");
        header.addHeader("Content-Length", String.valueOf(body.getLength()));
    }

    public void viewStyleSheet(String file) {
        this.status = OK;
        this.header = new ResponseHeader();
        this.body = new ResponseBody(ViewHelper.readStyleSheetFile(file));
        header.addHeader("Content-Type", "text/css");
        header.addHeader("Content-Length", String.valueOf(body.getLength()));
    }

    public void addHeader(String key, String value) {
        this.header.addHeader(key, value);
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeader() {
        return header.getHeader();
    }

    public byte[] getBody() {
        return body.getBody();
    }

    public void addCookie(String cookie) {
        this.header.addCookie(cookie);
    }

    public void write() {
        try {
            dos.writeBytes(makeStatus());
            List<String> headers = makeHeader();
            for (String header : headers) {
                dos.writeBytes(header);
            }
            dos.writeBytes(HEADER_END_STRING);
            dos.write(body.getBody(), 0, body.getLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String makeStatus() {
        return String.format("%s %s %s \r\n", STATUS_PREFIX, status.getCode(), status.getText());
    }

    private List<String> makeHeader() {
        List<String> response = new ArrayList<>();
        for (String key : this.getHeader().keySet()) {
            response.add(String.format("%s: %s\r\n", key, getHeader().get(key)));
        }
        return response;
    }

}

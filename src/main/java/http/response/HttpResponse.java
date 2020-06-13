package http.response;

import dto.Users;
import http.request.Cookie;
import http.request.Cookies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.FileView;
import view.RedirectView;
import view.StaticFileView;
import view.TemplateView;
import view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private View view;
    private Cookies cookies;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.header = new ResponseHeader();
        this.cookies = new Cookies();
    }

    public void sendRedirect(String redirectUrl) {
        this.status = FOUND;
        this.view = new RedirectView(redirectUrl);
        header.addHeader("Location", redirectUrl);
    }

    public void forward(String file) {
        this.status = OK;
        this.view = new FileView(file);
        header.addHeader("Content-Type", "text/html;charset=utf-8");
    }

    public void returnHandlebar(String path, Users users) {
        this.status = OK;
        this.view = new TemplateView(path, users);
        header.addHeader("Content-Type", "text/html;charset=utf-8");
    }

    public void viewStyleSheet(String file) {
        this.status = OK;
        this.view = new StaticFileView(file);
        header.addHeader("Content-Type", "text/css");
    }

    public void addHeader(String key, String value) {
        this.header.addHeader(key, value);
    }

    public void addCookie(String key, String value, String path) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        this.cookies.addCookie(cookie);
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeader() {
        return header.getHeader();
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public View getView() {
        return view;
    }

    public void setBody(byte[] content) {
        this.body = new ResponseBody(content);
    }

    public void write() {
        try {
            dos.writeBytes(makeStatus());
            this.header.addCookies(cookies);
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

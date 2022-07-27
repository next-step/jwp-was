package webserver.response;

import org.springframework.http.HttpStatus;
import webserver.Header;
import webserver.Cookie;
import webserver.request.Model;

import java.io.DataOutputStream;
import java.util.Map;

public class Response {
    private HttpStatus httpStatus;
    private byte[] body;
    private Cookie cookie;
    private Header header;
    private String locationPath;
    private Model model;

    public Response(HttpStatus httpStatus, byte[] body) {
        this.httpStatus = httpStatus;
        this.header = new Header();
        this.body = body;
        this.model = new Model();
    }

    public Response() {
        cookie = new Cookie();
        header = new Header();
        this.model = new Model();
    }

    public void sendStatus(DataOutputStream dos) {
        ResponseWriter.findResponseBodyWrite(this.httpStatus)
                .ifPresent(responseWrite -> responseWrite.write(this, dos));
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength(){
        return body.length;
    }

    public void makeStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getLocationPath() {
        return locationPath;
    }

    public void makeLocationPath(String path) {
        this.locationPath = path;
    }

    public void addModel(Model model) {
        this.model = model;
    }

    public Map<String, Object> getModelMap() {
        return model.getModelMap();
    }

    public void addBody(byte[] body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }
}

package response;

import org.springframework.http.HttpStatus;
import request.Cookie;
import request.Model;

import java.io.DataOutputStream;
import java.util.Objects;

public class Response {
    private HttpStatus httpStatus;
    private byte[] body;
    private Cookie cookie;
    private String locationPath;
    private Model model;

    public Response(HttpStatus httpStatus, byte[] body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public Response() {
        cookie = new Cookie();
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

    public Model getModelMap() {
        return model;
    }

    public void addBody(String body) {
        this.body = body.getBytes();
    }
}

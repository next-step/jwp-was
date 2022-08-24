package model.response;

import model.HttpHeaders;
import model.HttpProtocol;

public class HttpResponse {
    private HttpProtocol httpProtocol;
    private HttpHeaders httpHeaders;
    private String status;
    private String message;

    public void redirect(String location) {
        this.httpProtocol = new HttpProtocol("HTTP/1.1");
        this.status = "302";
        this.message = "OK";
        this.httpHeaders = new HttpHeaders().addLocation(location);
    }
}

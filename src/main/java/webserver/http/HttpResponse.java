package webserver.http;

import java.util.List;

public class HttpResponse {

    private int httpStatus = 200;

    private final HttpHeaders httpHeaders;

    private byte[] responseBody;

    public HttpResponse(){
        httpHeaders = new HttpHeaders();
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setHttpHeader(String name, String value) {
        this.httpHeaders.setHeader(name, value);
    }

    public List<String> getHttpHeaderLines(){
        return this.httpHeaders.getHeaderLines();
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }
}

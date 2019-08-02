package webserver.http;

import enums.HttpStatus;

import java.util.List;

public class HttpResponse {

    private HttpStatus httpStatus = HttpStatus.OK;

    private final HttpHeaders httpHeaders;

    private byte[] responseBody;

    public HttpResponse(){
        httpHeaders = new HttpHeaders();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
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
        setHttpHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(responseBody.length));
        this.responseBody = responseBody;
    }
}

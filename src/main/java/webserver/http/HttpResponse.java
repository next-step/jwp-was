package webserver.http;

import enums.HttpStatus;
import utils.FileIoUtils;
import utils.MimeTypeUtils;

import java.net.URL;
import java.util.List;

public class HttpResponse {

    private final HttpRequest httpRequest;

    private final HttpHeaders httpHeaders;

    private HttpStatus httpStatus = HttpStatus.OK;

    private byte[] responseBody;

    private HttpResponse(HttpRequest httpRequest){
        this.httpRequest = httpRequest;
        this.httpHeaders = new HttpHeaders();
    }

    public static HttpResponse of(HttpRequest httpRequest) {
        return new HttpResponse(httpRequest);
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

    public void redirect(String redirectUrl) {
        this.setHttpStatus(HttpStatus.FOUND);
        this.setHttpHeader(HttpHeaders.LOCATION, redirectUrl);
    }

    public void sendResource(URL resourceUrl){
        byte[] body = FileIoUtils.loadFileFromURL(resourceUrl);
        this.setResponseBody(body);

        String mimeType = MimeTypeUtils.guessContentTypeFromName(resourceUrl.getFile(), httpRequest.getHeader(HttpHeaders.ACCEPT));
        this.setHttpHeader(HttpHeaders.CONTENT_TYPE, mimeType);
    }
}

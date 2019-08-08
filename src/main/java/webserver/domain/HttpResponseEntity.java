package webserver.domain;

import webserver.http.HttpRequest;
import webserver.http.HttpStatus;

public class HttpResponseEntity {

    private HttpHeader httpHeader;
    private int resultCode;
    private String urlPath;
    private String version;
    private String resultBody;
    private String redirectUrl;
    private String cookie;

    public HttpResponseEntity(HttpHeader httpHeader, int resultCode, String version) {
        this.httpHeader = httpHeader;
        this.resultCode = resultCode;
        this.version = version;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getVersion() {
        return version;
    }

    public String getResultBody() {
        return resultBody;
    }

    public void setResultBody(String resultBody) {
        this.resultBody = resultBody;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public static HttpResponseEntity setStatusResponse(HttpRequest httpRequest,
                                                       HttpStatus status){
        HttpResponseEntity responseEntity =
                new HttpResponseEntity(httpRequest.getHttpHeader(),
                        status.getHttpStatusCode(),
                        httpRequest.getVersion());

        responseEntity.setResultBody(status.getHttpStatusMessage());
        return responseEntity;
    }
}

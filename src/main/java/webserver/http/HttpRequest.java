package webserver.http;

import webserver.converter.HttpHeaderConverter;
import java.util.HashMap;

public class HttpRequest {

    protected HttpHeader httpHeader;

    private HashMap<String, String> parameter;
    private String returnContent;
    private String location;
    private String cookie;

    public static HttpRequest parse(String httpMsg){
        HttpRequest request = new HttpRequest(new HttpHeader());
        new HttpHeaderConverter(request, httpMsg);
        return request;
    }

    public HttpRequest(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }

    public HttpHeader getHttpHeader() { return this.httpHeader; }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }

    public String getMethod() {
        return this.httpHeader.getMethod();
    }

    public void setMethod(String method) {
        this.httpHeader.setMethod(method);
    }

    public String getUrlPath() {
        return this.httpHeader.getUrlPath();
    }

    public void setUrlPath(String urlPath) {
        this.httpHeader.setUrlPath(urlPath);
    }

    public String getVersion() {
        return this.httpHeader.getVersion();
    }

    public void setVersion(String version) {
        this.httpHeader.setVersion(version);
    }

    public HashMap<String, String> getEtcHeader() {
        return this.httpHeader.getEtcHeader();
    }

    public void setEtcHeader(HashMap<String, String> etcHeader) {
        this.httpHeader.setEtcHeader(etcHeader);
    }

}

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

    public void initHeaderValue(String method, String urlPath, String version){
        this.httpHeader.initHeader(method, urlPath, version);
    }

    public void initRequestParameter(HashMap<String, String> paramMap){
        this.parameter = paramMap;
    }

    public HttpHeader getHttpHeader() { return this.httpHeader; }

    public String getCookie() {
        return cookie;
    }

    public String getLocation() {
        return location;
    }

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public String getMethod() {
        return this.httpHeader.getMethod();
    }

    public String getUrlPath() {
        return this.httpHeader.getUrlPath();
    }

    public String getVersion() {
        return this.httpHeader.getVersion();
    }

    public HashMap<String, String> getEtcHeader() {
        return this.httpHeader.getEtcHeader();
    }

    public void initEtcHeaderParameter(HashMap<String, String> etcHeader) {
        this.httpHeader.initEtcHeaderParameter(etcHeader);
    }
}

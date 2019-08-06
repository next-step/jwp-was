package webserver.domain;

import java.util.HashMap;

public class HttpEntity {

    private final HttpHeader httpHeader;

    private HashMap<String, String> parameter;
    private String returnContent;
    private String location;
    private String cookie;

    public HttpEntity() {
        this.httpHeader = new HttpHeader();
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

    public String getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(String returnContent) {
        this.returnContent = returnContent;
    }

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }

    public int getResultCode() {
        return httpHeader.getResultCode();
    }

    public void setResultCode(int resultCode) {
        this.httpHeader.setResultCode(resultCode);
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

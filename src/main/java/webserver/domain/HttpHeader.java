package webserver.domain;

import webserver.http.HttpStatus;

import java.util.HashMap;

public class HttpHeader {

    private int resultCode = HttpStatus.OK.getHttpStatusCode();
    private String method;
    private String urlPath;
    private String version;
    private HashMap<String, String> etcHeader;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, String> getEtcHeader() {
        return etcHeader;
    }

    public void setEtcHeader(HashMap<String, String> etcHeader) {
        this.etcHeader = etcHeader;
    }
}

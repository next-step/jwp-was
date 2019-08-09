package webserver.http;

import java.util.HashMap;

public class HttpHeader {

    public static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    private HttpStatus resultCode = HttpStatus.OK;
    private String method;
    private String urlPath;
    private String version = "HTTP/1.1";
    private HashMap<String, String> etcHeader;

    public HttpHeader(){}

    public HttpHeader(HttpStatus resultCode){
        this.resultCode = resultCode;
        this.version = DEFAULT_HTTP_VERSION;
    }

    public HttpHeader(HttpStatus resultCode, String version) {
        this.resultCode = resultCode;
        this.version = (version == null) ? DEFAULT_HTTP_VERSION : version;
    }

    public HttpStatus getResultCode() {
        return resultCode;
    }

    public void setResultCode(HttpStatus resultCode) {
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

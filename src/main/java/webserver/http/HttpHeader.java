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

    public HttpHeader(HttpStatus resultCode, String version, HashMap<String, String> etcHeader) {
        this.resultCode = resultCode;
        this.version = (version == null) ? DEFAULT_HTTP_VERSION : version;
        this.etcHeader = etcHeader;
    }

    public void initHeader(String method, String urlPath, String version){
        this.method = method;
        this.urlPath = urlPath;
        this.version = version;
    }

    public HttpStatus getResultCode() {
        return resultCode;
    }

    public String getMethod() {
        return method;
    }


    public String getUrlPath() {
        return urlPath;
    }

    public String getVersion() {
        return version;
    }

    public HashMap<String, String> getEtcHeader() {
        return etcHeader;
    }

    public void initEtcHeaderParameter(HashMap<String, String> etcHeader) {
        this.etcHeader = etcHeader;
    }
}

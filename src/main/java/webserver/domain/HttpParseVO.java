package webserver.domain;

import java.util.HashMap;

public class HttpParseVO {

    private String resultCode = "200";
    private String method;
    private String urlPath;
    private String version;
    private HashMap<String, String> parameter;
    private HashMap<String, String> etcHeader;
    private String returnContent;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(String returnContent) {
        this.returnContent = returnContent;
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

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }

    public HashMap<String, String> getEtcHeader() {
        return etcHeader;
    }

    public void setEtcHeader(HashMap<String, String> etcHeader) {
        this.etcHeader = etcHeader;
    }
}

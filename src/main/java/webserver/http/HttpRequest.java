package webserver.http;

import webserver.converter.HttpHeaderConverter;
import webserver.domain.HttpEntity;
import webserver.domain.HttpHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class HttpRequest {

    private HttpEntity httpEntity;
    private HttpController httpController;

    public HttpRequest(HttpController httpController) {
        this.httpController = httpController;
        this.httpEntity = new HttpEntity();
    }

    public void parse(String httpMsg)
            throws IOException, URISyntaxException{
            new HttpHeaderConverter(this, httpMsg);
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public HttpHeader getHttpHeader() { return httpEntity.getHttpHeader(); }

    public String getMethod(){
        return httpEntity.getMethod();
    }

    public String getUrlPath() {
        return httpEntity.getUrlPath();
    }

    public HashMap<String, String> getParameter() {
        return httpEntity.getParameter();
    }

    public String getReturnContent() {
        return httpEntity.getReturnContent();
    }

    public String getVersion() {
        return httpEntity.getVersion();
    }

    public int getResultCode() {
        return httpEntity.getResultCode();
    }

    public HashMap<String, String> getEtcHeader() {
        return httpEntity.getEtcHeader();
    }

    public HttpController getHttpController() { return httpController; }
}

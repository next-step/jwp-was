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

    public HttpRequest(HttpController httpController, String httpMsg) {
        this.httpController = httpController;
        this.httpEntity = new HttpEntity();

        try {
            new HttpHeaderConverter(this, httpMsg);
        }catch (IOException e){
            HttpResponse.setPageError(this);
        }catch (URISyntaxException e){
            HttpResponse.setPageNotFond(this);
        }
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public HttpHeader getHttpHeader(){
        return httpEntity.getHttpHeader();
    }

    public String getMethod(){
        return httpEntity.getMethod();
    }

    public String getUrlPath() {
        return httpEntity.getUrlPath();
    }

    public HashMap<String, String> getParameter() {
        return httpEntity.getParameter();
    }

    public void setCookie(String cookie) {
        httpEntity.setCookie(cookie);
    }

    public void setUrlPath(String urlPath) {
        httpEntity.setUrlPath(urlPath);
    }

    public String getReturnContent() {
        return httpEntity.getReturnContent();
    }

    public void setReturnContent(String remakeContent) {
        httpEntity.setReturnContent(remakeContent);
    }

    public String getVersion() {
        return httpEntity.getVersion();
    }

    public int getResultCode() {
        return httpEntity.getResultCode();
    }

    public String getLocation() {
        return httpEntity.getLocation();
    }

    public String getCookie() {
        return httpEntity.getCookie();
    }

    public void setResultCode(int httpStatusCode) {
        httpEntity.setResultCode(httpStatusCode);
    }

    public void setLocation(String location) {
        httpEntity.setLocation(location);
    }

    public HashMap<String, String> getEtcHeader() {
        return getEtcHeader();
    }

    public HttpController getHttpController() { return httpController; }
}

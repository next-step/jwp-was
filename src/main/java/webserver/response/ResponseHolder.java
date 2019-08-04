package webserver.response;

import webserver.request.RequestHolder;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ResponseHolder {

    private DataOutputStream dos;
    private RequestHolder requestHolder;
    private String viewName;
    private Map<String, String> cookies;

    public ResponseHolder(DataOutputStream dos, RequestHolder requestHolder) {
        this.dos = dos;
        this.requestHolder = requestHolder;
        this.cookies = new HashMap<>();
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public String getPath() {
        return requestHolder.getPath();
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public boolean hasCookie() {
        return ! cookies.isEmpty();
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public RequestHolder getRequestHolder() {
        return requestHolder;
    }
}

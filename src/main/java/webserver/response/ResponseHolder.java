package webserver.response;

import webserver.request.RequestHolder;

import java.io.DataOutputStream;

public class ResponseHolder {

    private DataOutputStream dos;
    private RequestHolder requestHolder;
    private String viewName;

    public ResponseHolder(DataOutputStream dos, RequestHolder requestHolder) {
        this.dos = dos;
        this.requestHolder = requestHolder;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getPath() {
        return requestHolder.getPath();
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public RequestHolder getRequestHolder() {
        return requestHolder;
    }
}

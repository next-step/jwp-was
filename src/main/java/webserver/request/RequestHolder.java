package webserver.request;

import webserver.URLQuery;

public class RequestHolder {
    private RequestLine requestLine;
    private RequestHeader requestHeader;

    public RequestHolder(RequestLine requestLine, RequestHeader requestHeader) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public URLQuery getUrlQuery() {
        return requestLine.getUrlQuery();
    }

}

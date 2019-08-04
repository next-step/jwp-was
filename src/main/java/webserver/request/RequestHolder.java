package webserver.request;

import webserver.Parameter;

import static java.util.Arrays.asList;
import static webserver.Parameter.of;

public class RequestHolder {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private Parameter mergedParameter;

    public RequestHolder(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.mergedParameter = of(asList(requestLine.getParameter(), requestBody.getParameter()));
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public Parameter getMergedParameter() {
        return mergedParameter;
    }

}

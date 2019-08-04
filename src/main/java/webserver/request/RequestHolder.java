package webserver.request;

import webserver.Parameter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static webserver.Parameter.of;

public class RequestHolder {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private Parameter mergedParameter;
    private Map<String, Object> attributes;

    public RequestHolder(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.mergedParameter = of(asList(requestLine.getParameter(), requestBody.getParameter()));
        this.attributes = new HashMap<>();
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

    public void addAttributes(String key, Object attribute) {
        this.attributes.put(key, attribute);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

}

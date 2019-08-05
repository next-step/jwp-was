package webserver.request;

import webserver.HttpParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static webserver.HttpParameter.of;

public class RequestHolder {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpParameter mergedHttpParameter;
    private Map<String, Object> attributes;

    public RequestHolder(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.mergedHttpParameter = of(asList(requestLine.getHttpParameter(), requestBody.getHttpParameter()));
        this.attributes = new HashMap<>();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public HttpParameter getMergedHttpParameter() {
        return mergedHttpParameter;
    }

    public void addAttributes(String key, Object attribute) {
        this.attributes.put(key, attribute);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getResponseContentType() {
        return Optional.of(requestHeader)
                .map(RequestHeader::getAccept)
                .map(accepts -> accepts.split(",")[0])
                .orElse("text/html");
    }

}

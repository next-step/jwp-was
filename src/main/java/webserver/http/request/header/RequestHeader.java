package webserver.http.request.header;

import webserver.http.request.method.HttpMethod;

public class RequestHeader {
    private final RequestLine requestLine;
    private final RequestHeaderInfos headerInfo;

    private RequestHeader(RequestLine requestLine, String[] headerInfos) {
        this.requestLine = requestLine;
        this.headerInfo = new RequestHeaderInfos(headerInfos);
    }

    public static RequestHeader create(String contents) {
        String[] headerInfos = contents.split("\n");

        return new RequestHeader(
                RequestLine.create(headerInfos[0]),
                headerInfos
        );
    }

    public HttpMethod httpMethod() {
        return requestLine.httpMethod();
    }

    public String index() {
        return requestLine.index();
    }
    public String requestParams(String key) {
        return requestLine.requestParams().getOrDefault(key, "");
    }

    public String version() {
        return requestLine.version();
    }

    public int contentLength() {
        return headerInfo.contentsLength();
    }

    public String host() {
        return headerInfo.host();
    }

    public String protocolVersion() {
        return requestLine.protocol() + "/" + requestLine.version();
    }

    public String cookie() {
        return headerInfo.cookie();
    }
}

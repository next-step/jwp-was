package webserver.header.request;

import webserver.header.request.requestline.RequestLine;

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

    public RequestLine requestLine() {
        return requestLine;
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
}

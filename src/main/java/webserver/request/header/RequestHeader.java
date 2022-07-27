package webserver.request.header;

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

    public String httpMethod() {
        return requestLine.httpMethod();
    }

    public String index() {
        return requestLine.index();
    }
    public String requestParams(String key) {
        return requestLine.requestParams().get(key);
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
}

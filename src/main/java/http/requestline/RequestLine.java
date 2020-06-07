package http.requestline;

import http.method.HttpMethod;
import http.requestline.protocol.Protocol;
import http.requestline.protocol.ProtocolSpec;
import lombok.Builder;
import lombok.Getter;

public class RequestLine {

    @Getter
    private final HttpMethod method;

    @Getter
    private final String path;
    private final ProtocolSpec protocolSpec;

    @Builder
    RequestLine(HttpMethod method, String path, ProtocolSpec protocolSpec) {
        this.method = method;
        this.path = path;
        this.protocolSpec = protocolSpec;
    }

    public Protocol getProtocol() {
        return protocolSpec.getProtocol();
    }

    public String getProtocolVersion() {
        return protocolSpec.getVersion();
    }
}

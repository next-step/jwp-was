package http.requestline;

import http.requestline.protocol.Protocol;
import http.requestline.protocol.ProtocolSpec;
import http.requestline.protocol.ProtocolSpecPool;
import lombok.Builder;
import lombok.Getter;

public class RequestLine {

    @Getter
    private final String method;

    @Getter
    private final String path;
    private final ProtocolSpec protocolSpec;

    @Builder
    public RequestLine(String method, String path, String protocolSpec) {
        this.method = method;
        this.path = path;
        this.protocolSpec = ProtocolSpecPool.find(protocolSpec);
    }

    public Protocol getProtocol() {
        return protocolSpec.getProtocol();
    }

    public String getProtocolVersion() {
        return protocolSpec.getVersion();
    }
}

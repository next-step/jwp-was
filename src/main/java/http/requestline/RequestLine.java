package http.requestline;

import http.method.HttpMethod;
import http.requestline.path.Path;
import http.requestline.protocol.Protocol;
import http.requestline.protocol.ProtocolSpec;
import lombok.Builder;
import lombok.Getter;

public class RequestLine {

    @Getter
    private final HttpMethod method;

    @Getter
    private final Path path;
    private final ProtocolSpec protocolSpec;

    @Builder
    RequestLine(HttpMethod method, Path path, ProtocolSpec protocolSpec) {
        this.method = method;
        this.path = path;
        this.protocolSpec = protocolSpec;
    }

    public boolean hasPathFileExtension() {
        return path.hasExtension();
    }

    public String getFilePath() {
        return path.getFilePath();
    }

    public String getQueryStringValue(String key) {
        return path.getQueryStringValue(key);
    }

    public String getMimeType() {
        return path.getMimeType();
    }

    public Protocol getProtocol() {
        return protocolSpec.getProtocol();
    }

    public String getProtocolVersion() {
        return protocolSpec.getVersion();
    }
}

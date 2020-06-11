package http.request.requestline;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.common.HttpMethod;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class RequestLine {
    @Getter
    private HttpMethod method;
    private Path path;
    private Protocol protocol;

    public RequestLine(HttpMethod method, Path path, Protocol protocol) {
        if (Objects.isNull(method)) {
            throw new IllegalArgumentException();
        }

        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(String requestLine) {
        if (StringUtils.isEmpty(requestLine)) {
            throw new IllegalArgumentException();
        }

        String[] splitLine = requestLine.split(" ");

        if (splitLine.length != 3) {
            throw new IllegalArgumentException();
        }

        return new RequestLine(HttpMethod.resolve(splitLine[0]), Path.of(splitLine[1]), Protocol.of(splitLine[2]));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("method는 ").append(method).append("\r\n");
        sb.append(path.toString());
        sb.append(protocol.toString());

        return sb.toString();
    }

    public String getPath() {
        return Optional.ofNullable(path)
            .map(Path::getPath)
            .orElse(null);
    }

    public Map<String, String> getQueryString() {
        return Optional.ofNullable(path)
            .map(Path::getQueryString)
            .orElse(null);
    }

    public String getProtocolStr() {
        return Optional.ofNullable(protocol)
            .map(Protocol::getProtocol)
            .orElse(null);
    }

    public String getVersion() {
        return Optional.ofNullable(protocol)
            .map(Protocol::getVersion)
            .orElse(null);
    }

    public Protocol getProtocol() {
        return protocol;
    }
}

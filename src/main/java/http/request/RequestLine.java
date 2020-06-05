package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.HttpMethod;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.stream.Collectors.toMap;

@Slf4j
public class RequestLine {
    @Getter
    private HttpMethod method;
    private Path path;
    private Protocol protocol;

    public static RequestLine of(String requestLine) {
        if (StringUtils.isEmpty(requestLine)) {
            throw new IllegalArgumentException();
        }

        String[] splitLine = requestLine.split(" ");

        if (splitLine.length != 3) {
            throw new IllegalArgumentException();
        }

        return new RequestLine(HttpMethod.resolve(splitLine[0]), Path.of(splitLine[1]), Protocol.of(splitLine[2]));
    }

    public RequestLine(HttpMethod method, Path path, Protocol protocol) {
        if (Objects.isNull(method)) {
            throw new IllegalArgumentException();
        }

        this.method = method;
        this.path = path;
        this.protocol = protocol;
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

    public String getProtocol() {
        return Optional.ofNullable(protocol)
            .map(Protocol::getProtocol)
            .orElse(null);
    }

    public String getVersion() {
        return Optional.ofNullable(protocol)
            .map(Protocol::getVersion)
            .orElse(null);
    }
}

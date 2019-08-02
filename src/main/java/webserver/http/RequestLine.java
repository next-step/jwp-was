package webserver.http;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RequestLine {

    private final String method;
    private final Path path;
    private final QueryParam queryParam;

    public RequestLine(String method, String path) {
        this.method = method;
        this.path = Path.from(path);
        this.queryParam = QueryParam.parse(this.path.getQueryString().orElse(""));
    }

    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split("\\s");
        return new RequestLine(values[0], values[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public String getUri() {
        return this.path.getUri();
    }

    public List<String> paramValues() {
        return this.queryParam.paramValues();
    }

    public Set<String> paramKeys() {
        return this.queryParam.paramKeys();
    }

    public Optional<String> getParam(String key) {
        return this.queryParam.get(key);
    }
}

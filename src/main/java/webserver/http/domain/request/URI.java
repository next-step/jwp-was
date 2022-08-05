package webserver.http.domain.request;

import webserver.http.domain.KeyValuePair;
import webserver.http.domain.exception.BadRequestException;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Objects;

public class URI {
    private static final String QUERY_PARAMETER_DELIMITER = "?";

    private final String path;
    private final Parameters queryParameters;

    public URI(String path) {
        this(path, new Parameters(new HashMap<>()));
    }

    public URI(String path, Parameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return queryParameters.get(key);
    }

    public void addParameters(Parameters parameters) {
        queryParameters.add(parameters);
    }

    public static URI from(String message) {
        if (Objects.isNull(message) || message.isBlank()) {
            throw new BadRequestException("URI는 빈값 혹은 null이 될 수 없습니다.");
        }
        KeyValuePair pathQueryParamPair = KeyValuePair.from(message, QUERY_PARAMETER_DELIMITER, false);
        String path = pathQueryParamPair.getKey();
        String queryParameters = pathQueryParamPair.getValue();
        return new URI(path, Parameters.from(queryParameters));
    }

    @Override
    public String toString() {
        return "URI{" +
                "path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                '}';
    }
}

package webserver;

import lombok.EqualsAndHashCode;
import utils.ValidUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@EqualsAndHashCode
public class RequestLine {

    private static final String BLANK = " ";
    private static final String URL_SEPARATOR = "\\?";
    private static final String ENTRY_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    private HttpMethod method;
    private String url;
    private Protocol protocol;
    private Map<String, String> queryParameters;

    public RequestLine(HttpMethod method, String url, Protocol protocol) {
        ValidUtils.notNull(method, "http method는 null일 수 없습니다");
        ValidUtils.notBlank(url, "http url은 blank일 수 없습니다");
        ValidUtils.notNull(protocol, "프로토콜 정보가 null일 수 없습니다");

        this.method = method;
        this.url = url;
        this.protocol = protocol;
        queryParameters = new HashMap<>();
    }

    public RequestLine(String requestLine) {
        ValidUtils.notBlank(requestLine, "http request line은 null일 수 없습니다");

        String[] split = requestLine.split(BLANK);
        this.method = HttpMethod.resolve(split[0]);
        this.url = split[1];

        String protocolNameAndVersion = split[2];
        this.protocol = new Protocol(protocolNameAndVersion);
        this.queryParameters = parseQueryString(url);
    }

    private Map<String, String> parseQueryString(String url) {
        ValidUtils.notBlank(url, "http url은 blank일 수 없습니다");

        String[] queryString = url.split(URL_SEPARATOR);
        if (isNonExistent(queryString)) {
            return Collections.emptyMap();
        }
        String urlParameters = queryString[1];
        return Arrays.stream(urlParameters.split(ENTRY_SEPARATOR))
                .map(entry -> entry.split(KEY_VALUE_SEPARATOR))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
    }

    private boolean isNonExistent(String[] queryString) {
        ValidUtils.notNull(queryString);
        return queryString.length <= 1;
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(queryParameters);
    }
}

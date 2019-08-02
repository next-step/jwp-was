package request;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static com.github.jknack.handlebars.internal.lang3.StringUtils.SPACE;
import static request.UriSplitter.*;

/**
 * Created by youngjae.havi on 2019-08-01
 */
public class RequestLine {
    private HttpMethod method;
    private String path;
    private MultiValueMap<String, String> queryStringMap;
    private String httpVersion;

    public RequestLine(HttpMethod method, String path, MultiValueMap<String, String> queryStringMap, String httpVersion) {
        this.method = method;
        this.path = path;
        this.queryStringMap = queryStringMap;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String requestLine) {
        String[] lines = requestLine.split(SPACE);
        String[] splitPathAndQuery = lines[1].split(START_QUERY.getSplitter());
        MultiValueMap<String, String> queryStringMap = new LinkedMultiValueMap<>();

        boolean hasQueryString = splitPathAndQuery.length > 1;
        if (hasQueryString) {
            String queryString = splitPathAndQuery[1];
            makeQueryString(queryString, queryStringMap);
        }

        return new RequestLine(HttpMethod.of(lines[0]), splitPathAndQuery[0], queryStringMap, lines[2]);
    }

    private static void makeQueryString(String queryString, MultiValueMap<String, String> queryStringMap) {
        String[] splitQuery = queryString.split(SPLIT_QUERY.getSplitter());
        if (splitQuery.length > 0) {
            Arrays.stream(splitQuery)
                    .map(query -> query.split(SPLIT_KEY_VALUE.getSplitter()))
                    .forEach(keyValue -> queryStringMap.put(keyValue[0], Arrays.asList(keyValue[1].split(COMMA.getSplitter()))));
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public MultiValueMap<String, String> getQueryString() {
        return this.queryStringMap;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", queryStringMap=" + queryStringMap +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}

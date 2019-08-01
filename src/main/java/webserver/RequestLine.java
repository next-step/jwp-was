package webserver;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;

import static com.github.jknack.handlebars.internal.lang3.StringUtils.SPACE;
import static utils.StringUtils.*;

/**
 * Created by youngjae.havi on 2019-08-01
 */
public class RequestLine {
    private String method;
    private String path;
    private MultiValueMap<String, String> queryStringMap;
    private String httpVersion;

    public RequestLine(String method, String path, MultiValueMap<String, String> queryStringMap, String httpVersion) {
        this.method = method;
        this.path = path;
        this.queryStringMap = queryStringMap;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String requestLine) {
        String[] lines = requestLine.split(SPACE);
        String[] splitPathAndQuery = lines[1].split(START_QUERY);
        String queryString = splitPathAndQuery[1];
        MultiValueMap<String, String> queryStringMap = new LinkedMultiValueMap<>();

        if (!StringUtils.isEmpty(queryString)) {
            makeQueryString(queryString, queryStringMap);
        }

        return new RequestLine(lines[0], splitPathAndQuery[0], queryStringMap, lines[2]);
    }

    private static void makeQueryString(String queryString, MultiValueMap<String, String> queryStringMap) {
        String[] splitQuery = queryString.split(SPLIT_QUERY);
        if (splitQuery.length > 0) {
            mappingQuery(queryStringMap, splitQuery);
        }
    }

    private static void mappingQuery(MultiValueMap<String, String> queryStringMap, String[] splitQuery) {
        for (String query : splitQuery) {
            String[] keyValue = query.split(SPLIT_KEY_VALUE);
            queryStringMap.put(keyValue[0], Arrays.asList(keyValue[1].split(COMMA)));
        }
    }

    public String getMethod() {
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
}

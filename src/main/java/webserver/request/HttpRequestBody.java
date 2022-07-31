package webserver.request;

import static utils.IOUtils.readData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import webserver.utils.QueryStrings;

public class HttpRequestBody {

    private static final String EMPTY_VALUE = "";
    private final Map<String, String> paramMap;

    private HttpRequestBody() {
        this(Collections.emptyMap());
    }

    private HttpRequestBody(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public static HttpRequestBody createEmpty() {
        return new HttpRequestBody();
    }

    public static HttpRequestBody of(String queryStrings) {
        validate(queryStrings);
        return new HttpRequestBody(QueryStrings.toMap(queryStrings));
    }

    public static HttpRequestBody of(BufferedReader br, String contentLength) throws IOException {
        return HttpRequestBody.of(readData(br, Integer.parseInt(contentLength)));
    }

    public static HttpRequestBody of(BufferedReader br, int contentLength) throws IOException {
        return HttpRequestBody.of(readData(br, contentLength));
    }

    private static void validate(String body) {
        Objects.requireNonNull(body);
    }

    public Map<String, String> map() {
        return this.paramMap;
    }

    public String getParameter(String key) {
        return paramMap.getOrDefault(key, EMPTY_VALUE);
    }

    public boolean hasEmptyParams() {
        return paramMap.isEmpty();
    }
}

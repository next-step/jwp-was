package webserver.request;

import static utils.IOUtils.readData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import webserver.utils.QueryStrings;

public class HttpRequestBody {

    private final Map<String, String> map;

    private HttpRequestBody() {
        this(Collections.emptyMap());
    }

    private HttpRequestBody(Map<String, String> map) {
        this.map = map;
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
        return this.map;
    }

}

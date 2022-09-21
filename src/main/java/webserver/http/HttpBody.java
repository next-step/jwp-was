package webserver.http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpBody {

    private static final String DELIMITER_KEY = "&";
    private static final String DELIMITER_VALUE = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> contents;

    private HttpBody(Map<String, String> contents) {
        this.contents = contents;
    }

    public static HttpBody of(BufferedReader bufferedReader, int contentLength) throws IOException {
        String request = IOUtils.readData(bufferedReader, contentLength);
        return HttpBody.from(request);
    }

    public static HttpBody from(String request) {
        if (request == null || request.length() == 0) {
            return HttpBody.empty();
        }
        Map<String, String> contents = Arrays.stream(request.split(DELIMITER_KEY))
                .map(it -> it.split(DELIMITER_VALUE))
                .collect(Collectors.toMap(e -> e[KEY], e -> e[VALUE]));
        return new HttpBody(contents);
    }

    public static HttpBody empty() {
        return new HttpBody(Collections.emptyMap());
    }

    public Map<String, String> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "HttpBody{" +
                "contents=" + contents +
                '}';
    }
}

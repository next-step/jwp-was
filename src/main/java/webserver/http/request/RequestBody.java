package webserver.http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {

    private static final String DELIMITER_KEY = "&";
    private static final String DELIMITER_VALUE = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> contents;

    private RequestBody(Map<String, String> contents) {
        this.contents = contents;
    }

    public static RequestBody empty() {
        return new RequestBody(Collections.emptyMap());
    }

    public static RequestBody from(String request) {
        if (request == null || request.length() == 0) {
            return RequestBody.empty();
        }
        Map<String, String> contents = Arrays.stream(request.split(DELIMITER_KEY))
                .map(it -> it.split(DELIMITER_VALUE))
                .collect(Collectors.toMap(e -> e[KEY], e -> e[VALUE]));
        return new RequestBody(contents);
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

package webserver.http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class RequestBody {

    private final Map<String, String > values;

    public RequestBody(Map<String, String> values) {
        this.values = values;
    }

    public static RequestBody of(BufferedReader bufferedReader, int contentLength) throws IOException {
        if (contentLength == 0) {
            return new RequestBody(Collections.emptyMap());
        }
        String readData = IOUtils.readData(bufferedReader, contentLength);
        Map<String, String> values = Arrays.stream(readData.split("&"))
                                            .map(token -> token.split("="))
                                            .collect(toMap(strings -> strings[0], strings -> strings[1]));
        return new RequestBody(values);
    }

    public String get(String key) {
        return values.get(key);
    }
}

package webserver.http.request;

import utils.DecoderUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {

    private static final String CONTENT_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> contents;

    public RequestBody(Map<String, String> contents) {
        this.contents = contents;
    }

    public static RequestBody emptyInstance(){
        return new RequestBody(Collections.EMPTY_MAP);
    }

    public static RequestBody parseFrom(final String body) {
        if (body.isEmpty() || body.isBlank() || body == null) {
            return new RequestBody(Collections.EMPTY_MAP);
        }

        return new RequestBody(
                Arrays.stream(body.split(CONTENT_DELIMITER))
                        .map(value -> value.split(KEY_VALUE_DELIMITER))
                        .collect(Collectors.toMap(entry -> DecoderUtils.decode(entry[0]), entry -> DecoderUtils.decode(entry[1])))
        );
    }

    public Map<String, String> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "contents=" + contents +
                '}';
    }
}

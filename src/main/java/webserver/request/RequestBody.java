package webserver.request;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.StringUtils;

public class RequestBody {
    private static final String BODY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> body;

    public RequestBody(String bodyString) {
        this.body = Stream.of(bodyString.split(BODY_DELIMITER))
                .map(data -> data.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(value -> value[0], value -> StringUtils.decodeUrlEncoding(value[1])));
    }

    public String getValue(String key) {
        return body.get(key);
    }
}

package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestBody {
    private final static String QUERY_STRING_DELIMITER = "&";
    private final static String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> parameter;

    public RequestBody() {
        this.parameter = Collections.emptyMap();
    }

    private RequestBody(Map<String, String> input) {
        this.parameter = input;
    }

    public static RequestBody from(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return new RequestBody();
        }

        try {
            Map<String, String> collect = Arrays.stream(input.split(QUERY_STRING_DELIMITER))
                    .map(query -> query.split(KEY_VALUE_DELIMITER))
                    .collect(Collectors.toUnmodifiableMap(

                            keyValue -> keyValue[0],
                            keyValue -> keyValue[1]
                    ));

            return new RequestBody(collect);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String get(String key) {
        return Optional.ofNullable(parameter.get(key))
                .orElseThrow(IllegalArgumentException::new);
    }
}

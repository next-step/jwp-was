package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestBody {
    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    public final static String MAP_KEY_VALUE_SEPARATOR = "=";
    public final static String REQUEST_BODY_SEPARATOR = "&";

    private final Map<String, String> requestBodyMap;

    public RequestBody(String value) {
        this.requestBodyMap = StringUtils.isEmpty(value) ? Collections.emptyMap() : parse(value);
        logger.debug("requestBody : {}", this.requestBodyMap);
    }

    public RequestBody(String... values) {
        if (values.length % 2 != 0) {
            throw new InvalidRequestException("RequestBody");
        }

        Map<String, String> map = new HashMap<>();

        int i = 0;
        while (i < values.length) {
            map.put(values[i++], values[i++]);
        }

        this.requestBodyMap = map;
    }

    private Map<String, String> parse(String value) {
        return Stream.of(StringUtils.split(value, REQUEST_BODY_SEPARATOR))
                .map(keyValue -> StringUtils.split(keyValue, MAP_KEY_VALUE_SEPARATOR))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }

    public Map<String, String> getRequestBodyMap() {
        return requestBodyMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(requestBodyMap, that.requestBodyMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestBodyMap);
    }
}

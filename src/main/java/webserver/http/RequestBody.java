package webserver.http;

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

import static model.Constant.KEY_VALUE_SPERATOR;
import static model.Constant.QUERY_STRING_SPERATOR;

public class RequestBody {
    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    private final Map<String, String> requestBodyEntry;

    public RequestBody(String value) {
        logger.debug("RequestBody : {}", value);
        this.requestBodyEntry = StringUtils.isEmpty(value) ? Collections.emptyMap() : parse(value);
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

        this.requestBodyEntry = map;
    }

    private Map<String, String> parse(String value) {
        return Stream.of(StringUtils.split(value, QUERY_STRING_SPERATOR))
                .map(keyValue -> StringUtils.split(keyValue, KEY_VALUE_SPERATOR))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }

    public Map<String, String> getRequestBodyEntry() {
        return requestBodyEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(requestBodyEntry, that.requestBodyEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestBodyEntry);
    }
}

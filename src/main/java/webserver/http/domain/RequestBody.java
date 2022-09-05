package webserver.http.domain;

import java.util.*;
import java.util.stream.Collectors;

import static webserver.http.domain.RequestParams.KEY_VALUE_DELIMITER;

public class RequestBody {

    private static final String BODY_DELIMITER = "&";
    public static final String EMPTY_STRING = "";
    private final Map<String, String> bodies;

    public RequestBody() {
        this.bodies = new HashMap<>();
    }

    public RequestBody(String bodyString) {
        this.bodies = create(bodyString);
    }

    public Map<String, String> bodies() {
        return Collections.unmodifiableMap(bodies);
    }

    private Map<String, String> create(String bodyString) {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(bodyString.split(BODY_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .forEach(params -> {
                    putParams(map, params);
                });
        return map;
    }

    private void putParams(Map<String, String> map, String[] params) {
        if (params.length == 1) {
            map.put(params[0], EMPTY_STRING);
            return;
        }
        map.put(params[0], params[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(bodies, that.bodies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bodies);
    }
}

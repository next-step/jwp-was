package webserver.http.domain;

import java.util.*;
import java.util.stream.Collectors;

import static webserver.http.domain.RequestParams.KEY_VALUE_DELIMITER;

public class RequestBody {

    private static final String BODY_DELIMITER = "&";
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
        return Arrays.stream(bodyString.split(BODY_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .peek(this::validateBodyValue)
                .collect(Collectors.toMap(params -> params[0], params -> params[1]));
    }

    private void validateBodyValue(String[] requestBodies) {
        if (requestBodies.length != 2 || requestBodies[0].isEmpty() || requestBodies[1].isEmpty()) {
            throw new IllegalArgumentException("Body Value의 값이 유효하지 않습니다. key=value 형태여야 합니다.");
        }
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

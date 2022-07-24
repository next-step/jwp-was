package http.request;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

public class Headers {

    private static final String DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> values;

    public Headers(List<String> values) {
        this.values = values.stream()
            .map(it -> it.split(DELIMITER))
            .map(it -> Map.entry(it[0], it[1]))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean hasBody() {
        var value = values.get(CONTENT_LENGTH);

        if (value == null) {
            return false;
        }

        var length = Integer.parseInt(value);
        return length > 0;
    }

    public int contentLength() {
        var value = values.get(CONTENT_LENGTH);

        if (value == null) {
            return 0;
        }

        return Integer.parseInt(value);
    }
}

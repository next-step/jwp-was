package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class RequestBody {

    static final RequestBody EMPTY = new RequestBody("");

    private final Map<String, String> body;

    RequestBody(String body) {
        this.body = Parser.parseBody(body);
    }

    String getValue(String name) {
        return body.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    private static class Parser {

        private static Map<String, String> parseBody(String body) {
            if (body == null || body.isEmpty()) {
                return Collections.emptyMap();
            }

            return Arrays.stream(body.split("&"))
                    .map(bodyEntry -> bodyEntry.split("="))
                    .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        }

    }
}

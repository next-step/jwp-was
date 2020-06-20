package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@EqualsAndHashCode
@AllArgsConstructor
public class RequestBody {

    private Map<String, String> values;

    public static RequestBody of(String data) {
        Map<String, String> entries = Arrays.stream(data.split("&"))
                .map(entry -> entry.split("="))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
        return new RequestBody(entries);
    }

    public String get(String name) {
        return values.get(name);
    }
}

package webserver.http;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cookie {

    private Map<String, String> entries = new HashMap<>();

    public Cookie(String cookieText) {
        this.entries = Arrays.stream(cookieText.split("; "))
                .map(entry -> entry.split("="))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
    }

    public void add(String key, String value) {
        entries.put(key, value);
    }

    public String get(String key) {
        String value = entries.get(key);
        return Objects.isNull(value) ? null : value;
    }
}

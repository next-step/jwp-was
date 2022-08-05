package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;

public class Cookies {

    private final Set<Cookie> values;

    public Cookies(Set<Cookie> values) {
        this.values = values;
    }

    public Set<Cookie> getValues() {
        return ImmutableSet.copyOf(values);
    }

    public Optional<String> getCookie(String key) {
        return values.stream()
            .filter(it -> it.isEquals(key))
            .map(Cookie::getValue)

            .findAny();
    }

    public void putCookie(String key, String value) {
        var cookie = new Cookie(key, value);
        values.add(cookie);
    }

    public void write(DataOutputStream dos) throws IOException {
        for (Cookie cookie : values) {
            var prefix = String.format("Set-Cookie: %s=%s", cookie.getKey(), cookie.getValue());

            var cookieResponse = Stream.concat(Stream.of(prefix), cookie.getOptions().stream())
                .collect(Collectors.joining("; "));

            dos.writeBytes(cookieResponse + "\r\n");
        }
    }
}
package http;

import java.util.Optional;
import java.util.Set;

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
}
package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cookie {

    private String name;

    private String value;

    private String path;

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public Cookie(String name, String value) {
        this(name, value, "");
    }

    public static List<Cookie> listOf(String cookieValues) {
        if (cookieValues.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(cookieValues.split("; "))
                .map(cookie -> cookie.split("="))
                .map(entry -> new Cookie(entry[0], entry[1]))
                .collect(Collectors.toUnmodifiableList());
    }

    boolean equalName(String name) {
        return this.name.equals(name);
    }

    public String getValue() {
        return value;
    }

    String getValues() {
        return name + "=" + value + "; " + "Path" + "=" + path;
    }

}

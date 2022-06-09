package webserver;

import java.util.Arrays;

public enum Protocol {
    HTTP_1_1("HTTP", "1.1", "HTTP/1.1");

    private final String name;
    private final String version;
    private final String alias;

    Protocol(final String name, final String version, final String alias) {
        this.name = name;
        this.version = version;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public static Protocol findByAlias(String alias) {
        return Arrays.stream(values())
                .filter(value -> value.alias.equals(alias))
                .findAny()
                .orElseThrow();
    }

}

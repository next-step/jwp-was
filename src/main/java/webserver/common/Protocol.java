package webserver.common;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import webserver.common.exception.IllegalProtocolException;

public enum Protocol {
    HTTP_1_1("HTTP", "1.1");

    private final String name;
    private final String version;

    private static final Map<String, Protocol> mapper = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(
                    protocol -> protocol.toString(),
                    protocol -> protocol
            ));

    Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public static Protocol from(String protocol) {
        return Optional.ofNullable(mapper.get(protocol))
                .orElseThrow(() -> new IllegalProtocolException(protocol));
    }

    @Override
    public String toString() {
        return String.format("%s/%s", name, version);
    }
}

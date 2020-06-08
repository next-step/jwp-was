package webserver;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class Protocol {

    private static final String SLASH = "/";

    private String name;
    private String version;

    public Protocol(String protocol) {
        String[] split = protocol.split(SLASH);
        this.name = split[0];
        this.version = split[1];
    }
}

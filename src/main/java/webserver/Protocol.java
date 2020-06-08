package webserver;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import utils.StringUtils;

@AllArgsConstructor
@EqualsAndHashCode
public class Protocol {

    private static final String PROTOCOL_SEPARATOR = "/";

    private String name;
    private String version;

    public Protocol(String protocol) {
        StringUtils.isBlank(protocol);

        String[] split = protocol.split(PROTOCOL_SEPARATOR);
        this.name = split[0];
        this.version = split[1];
    }
}

package http.request;

import lombok.Getter;

@Getter
public class Protocol {
    private static final String PROTOCOL_REGEX = "/";
    private String protocol;
    private String version;

    private Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static Protocol getInstance(String value) {
        String[] protocolValues = value.split(PROTOCOL_REGEX);
        return new Protocol(protocolValues[0], protocolValues[1]);
    }
}

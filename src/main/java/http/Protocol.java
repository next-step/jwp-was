package http;

public class Protocol {

    private static final String PROTOCOL_DELIMITER = "/";

    private final String name;
    private final String version;

    public Protocol(String protocol) {
        String[] tokens = protocol.split(PROTOCOL_DELIMITER);
        this.name = tokens[0];
        this.version = tokens[1];
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}

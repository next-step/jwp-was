package webserver.request;

public class Protocol {
    private static final String DELIMITER = "/";

    private final String name;
    private final String version;

    public Protocol(String protocol) {
        this.name = protocol.split(DELIMITER)[0];
        this.version = protocol.split(DELIMITER)[1];
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}

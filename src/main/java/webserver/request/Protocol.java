package webserver.request;

public class Protocol {
    private static final String DELIMITER = "/";

    private final String name;
    private final String version;

    public Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public Protocol(String protocol) {
        String[] splitProtocol = protocol.split(DELIMITER);

        this.name = splitProtocol[0];
        this.version = splitProtocol[1];
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}

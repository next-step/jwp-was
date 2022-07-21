package webserver.http.request;

public class Protocol {
    private final String type;
    private final String version;

    public Protocol(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }
}

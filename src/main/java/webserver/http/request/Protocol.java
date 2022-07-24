package webserver.http.request;

public class Protocol {
    private final String type;
    private final String version;

    public Protocol(String type, String version) {
        this.type = type;
        this.version = version;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}

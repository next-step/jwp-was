package webserver.http.domain;

public class Protocol {
    private final String type;
    private final String version;

    public Protocol(String type, String version) {
        this.type = type;
        this.version = version;
    }
    
    public static Protocol http1Point1() {
        return new Protocol("HTTP", "1.1");
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}

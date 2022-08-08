package webserver;

public class Protocol {

    public static final int PROTOCOL_INDEX = 0;
    public static final int VERSION_INDEX = 1;
    public static final String REGEX_SLASH = "/";

    private String protocol;
    private String version;

    public Protocol(String request) {
        this.protocol = parsingProtocol(request);
        this.version = parsingVersion(request);
    }

    private String parsingProtocol(String request) {
        return request.split(REGEX_SLASH)[PROTOCOL_INDEX];
    }

    private String parsingVersion(String request) {
        return request.split(REGEX_SLASH)[VERSION_INDEX];
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}

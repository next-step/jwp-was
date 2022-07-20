package webserver.enums;

public enum RequestProtocol {
    HTTP_1_1("HTTP", "1.1");

    private final String protocol;
    private final String version;

    RequestProtocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public String protocol() {
        return protocol;
    }

    public String version() {
        return version;
    }

    public static RequestProtocol of(String protocolString) {
        if (protocolString.split("/").length < 2) {
            throw new IllegalArgumentException("Invalid protocol and version string");
        }

        return RequestProtocol.valueOf(protocolString
            .replaceAll("/", "_")
            .replaceAll("\\.", "_")
        );
    }

}

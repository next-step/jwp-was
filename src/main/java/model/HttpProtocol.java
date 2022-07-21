package model;

public class HttpProtocol {
    public static String DELIMITER = "/";
    public static int PROTOCOL_INDEX = 0;
    public static int VERSION_INDEX = 1;

    private String protocol;
    private String version;

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public HttpProtocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static HttpProtocol Instance(String httpProtocol) {
        String[] protocolAndVersion = httpProtocol.split(DELIMITER);

        return new HttpProtocol(protocolAndVersion[PROTOCOL_INDEX], protocolAndVersion[VERSION_INDEX]);
    }

}

package webserver;

public enum HttpVersion {
    HTTP1_1("HTTP/1.1");

    private String version;

    private HttpVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}

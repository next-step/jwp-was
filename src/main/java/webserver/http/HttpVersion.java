package webserver.http;

public enum HttpVersion {

    HTTP1_1("HTTP/1.1");

    private final String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }
}

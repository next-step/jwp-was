package webserver;

public class RequestLine {
    private String[] values;
    public RequestLine(final String text) {
        values = text.split(" ");
    }

    public String getMethod() {
        return values[0];
    }

    public String getPath() {
        return values[1];
    }

    public String getProtocol() {
        return values[2].split("/")[0];
    }

    public String getProtocolVersion() {
        return values[2].split("/")[1];
    }
}

package webserver;

public class RequestLine {
    private String[] values;

    private String[] protocol;

    public RequestLine(final String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        String[] values = text.split(" ");

        if (values.length != 3) {
            throw new IllegalArgumentException();
        }

        String[] protocol = values[2].split("/");
        if (protocol.length != 2) {
            throw new IllegalArgumentException();
        }

        this.values = values;
        this.protocol = protocol;
    }

    public String getMethod() {
        return values[0];
    }

    public String getPath() {
        return values[1];
    }

    public String getProtocol() {
        return protocol[0];
    }

    public String getProtocolVersion() {
        return protocol[1];
    }
}

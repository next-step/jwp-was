package webserver;

public class RequestLine {
    public static RequestLine parse(final String httpRequestStartLine) {
        return new RequestLine();
    }

    public String getMethod() {
        return "GET";
    }

    public String getPath() {
        return "/users";
    }

    public String getProtocole() {
        return "HTTP";
    }

    public String getVersion() {
        return "1.1";
    }
}

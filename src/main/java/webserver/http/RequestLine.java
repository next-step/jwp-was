package webserver.http;

public class RequestLine {

    private final String method;
    private final String path;

    public RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
    }


    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split("\\s");
        return new RequestLine(values[0], values[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

}

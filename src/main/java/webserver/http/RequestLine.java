package webserver.http;

public class RequestLine {
    private final String method;
    private final String path;

    public static RequestLine parse(String requestString) {
        String[] splited = requestString.split(requestString);
        String method = splited[0];
        String path = splited[1];
        //String protocol = splited[2];
        return new RequestLine(method, path);
    }

    private RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }
}

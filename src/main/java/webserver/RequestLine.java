package webserver;

public class RequestLine {

    static final int INDEX_OF_METHOD = 0;
    static final int INDEX_OF_URL = 1;
    static final int INDEX_OF_VERSION = 2;

    private HttpMethod method;
    private HttpURL url;
    private String version;

    private RequestLine(HttpMethod method, HttpURL url, String version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    static RequestLine parse(String request) {
        String[] requests = request.split(" ");
        return new RequestLine(
                HttpMethod.valueOf(requests[INDEX_OF_METHOD]),
                HttpURL.of(requests[INDEX_OF_URL]),
                requests[INDEX_OF_VERSION]
        );
    }

    HttpMethod getMethod() {
        return method;
    }

    HttpURL getUrl() {
        return url;
    }

    String getVersion() {
        return version;
    }
}

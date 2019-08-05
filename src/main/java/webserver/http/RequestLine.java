package webserver.http;

import enums.HttpMethod;

public class RequestLine {

    private final HttpMethod method;
    private final String path;
    private final String version;

    private RequestLine(String method, String path, String version) {
        this.method = HttpMethod.parse(method);
        this.path = path;
        this.version = version;
    }


    public static RequestLine parse(String requestLine){

        String[] lineValues = requestLine.split(" ");

        if(lineValues == null) {
            throw new IllegalArgumentException("request line 이상함");
        }

        if(lineValues.length != 3) {
            throw new IllegalArgumentException("request line 이상함");
        }

        return new RequestLine(lineValues[0], lineValues[1], lineValues[2]);
    }


    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }


}

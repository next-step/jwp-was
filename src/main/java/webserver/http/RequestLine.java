package webserver.http;

public class RequestLine {

    private final String method;
    private final String path;
    private final String version;

    private RequestLine(String method, String path, String version) {
        this.method = method;
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


    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }


}

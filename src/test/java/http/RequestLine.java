package http;

import java.util.Map;

public class RequestLine {
    final String method;
    final String path;
    final Protocol protocol;

//    public RequestLine(String value, String value1, String s, String s1) {
//        method = value;
//        path = value1;
//        protocol = s;
//        version = s1;
//    }

    public RequestLine(String value, String value1, Protocol protocol) {
        this.method = value;
        this.path = value1;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameterString() {
        String[] split = this.path.split("\\?");
        return split[1];
    }

//    public Map<String, String> getParameter() {
//
//    }

//    public String getProtocol() {
//        return protocol;
//    }
//
//    public String getVersion() {
//        return version;
//    }
}

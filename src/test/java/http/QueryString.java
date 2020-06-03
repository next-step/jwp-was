package http;

public class QueryString {
    private String method;
    private String path;
    private String parameterString;
    public Protocol protocol;

    public QueryString(String value, String s, String s1, Protocol protocol) {
        this.method = value;
        this.path = s;
        this.parameterString = s1;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameterString() {
        return parameterString;
    }

    public String getParameter(String userId) {
        return null;
    }
}

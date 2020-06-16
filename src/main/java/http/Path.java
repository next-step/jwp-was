package http;

public class Path {
    private final String url;
    private final String requestParameter;

    public Path(String url, String requestParameter) {
        this.url = url;
        this.requestParameter = requestParameter;
    }

    public Path(String url) {
        this.url = url;
        this.requestParameter = null;
    }

    public static Path of(String value) {
        if (value == null)
            return null;
        String[] values = value.split("\\?");
        if (values.length == 2)
            return new Path(values[0], values[1]);
        return new Path(values[0]);
    }

    public String getUrl() {
        return url;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

}

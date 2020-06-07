package http;

public class Path {
    private final String url;
    private final String requestParameter;

    public Path(String url, String requestParameter) {
        this.url = url;
        this.requestParameter = requestParameter;
    }

    public static Path of(String value) {
        String[] values = value.split("\\?");
        return new Path(values[0], values[1]);
    }

    public String getUrl() {
        return url;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

}

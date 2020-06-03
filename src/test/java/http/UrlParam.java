package http;

public class UrlParam {
    private final String url;
    private final String parameters;

    public UrlParam(String s) {
        String[] split = s.split("\\?");
        this.url = split[0];
        this.parameters = split[1];
    }

    public UrlParam(String s, String s1) {
        this.url = s;
        this.parameters = s1;
    }

    public String getUrl() {
        return this.url;
    }

    public String getParameters() {
        return this.parameters;
    }
}

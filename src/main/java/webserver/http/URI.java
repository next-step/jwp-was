package webserver.http;

public class URI {
    private String path;
    private String url;
    private QueryString queryString;

    private URI(String url, QueryString queryString) {
        this.path = parsePath(url);
        this.url = url;
        this.queryString = queryString;
    }

    private String parsePath(String url) {
        int lastIndex = url.contains("?") ? url.indexOf("?") : url.length();
        return url.substring(0, lastIndex);
    }

    public static URI parse(String path) {
        return new URI(path, QueryString.parse(path));
    }

    public String getPath() {
        return this.path;
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

    public String getUrl() {
        return this.url;
    }
}

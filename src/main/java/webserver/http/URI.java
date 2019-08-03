package webserver.http;

import utils.StringDecoder;

public class URI {
    private String path;
    private String url;
    private QueryString queryString;

    private URI(String url, QueryString queryString) {
        this.path = parsePath(url);
        this.url = url;
        this.queryString = queryString;
    }

    public static URI parse(String uriString) {
        String decodedUrl = StringDecoder.decode(uriString);
        return new URI(decodedUrl, QueryString.parse(decodedUrl));
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

    private String parsePath(String url) {
        int lastIndex = url.contains("?") ? url.indexOf("?") : url.length();
        return url.substring(0, lastIndex);
    }

    @Override
    public String toString() {
        return url;
    }
}

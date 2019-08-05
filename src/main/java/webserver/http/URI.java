package webserver.http;

import utils.StringDecoder;

public class URI {
    private static final String PATH_QUERY_STRING_SEPARATOR = "?";

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
        int lastIndex = url.contains(PATH_QUERY_STRING_SEPARATOR) ? url.indexOf(PATH_QUERY_STRING_SEPARATOR) : url.length();
        return url.substring(0, lastIndex);
    }

    @Override
    public String toString() {
        return url;
    }
}

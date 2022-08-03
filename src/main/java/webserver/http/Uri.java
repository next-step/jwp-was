package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uri {
    private static final Logger log = LoggerFactory.getLogger(Uri.class);
    private final String path;
    private final String queryString;

    public Uri(String uri) {
        String queryString = null;
        if (uri.contains("?")) {
            queryString = uri.split("\\?")[1];
        }
        this.path = uri.split("\\?")[0];
        this.queryString = queryString;
    }

    public String getPath() {
        return path;
    }

    public String  getQueryString() {
        return queryString;
    }


}

package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uri {
    private static final Logger log = LoggerFactory.getLogger(Uri.class);
    private final String path;
    private final QueryStringParser queryString;

    public Uri(String uri) {
        QueryStringParser queryString = null;
        if (uri.contains("?")) {
            queryString = new QueryStringParser(uri.split("\\?")[1]);
        }
        this.path = uri.split("\\?")[0];
        this.queryString = queryString;
    }

    public String getPath() {
        return path;
    }

    public QueryStringParser getQueryString() {
        return queryString;
    }


}

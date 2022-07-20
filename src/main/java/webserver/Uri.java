package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uri {
    private static final Logger log = LoggerFactory.getLogger(Uri.class);
    private final String path;
    private final QueryStringParser queryString;

    public Uri(String uri) {
        if (!uri.contains("?")) {
            log.info("Query가 없습니다.");
            this.path = uri;
            this.queryString = null;
        }
        else {
            this.path = uri.split("\\?")[0];
            this.queryString = new QueryStringParser(uri.split("\\?")[1]);
        }
    }

    public String getPath() {
        return path;
    }

    public QueryStringParser getQueryString() {
        return queryString;
    }


}

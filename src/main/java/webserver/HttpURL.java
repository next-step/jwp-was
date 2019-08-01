package webserver;

import java.util.regex.Pattern;

public class HttpURL {

    private static final String DELIMITER = "?";
    private String path;
    private QueryString queryString;

    private HttpURL(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    static HttpURL of(String path) {
        String[] content = path.split(Pattern.quote(DELIMITER));

        if(path.contains(DELIMITER)){
            return new HttpURL(content[0], QueryString.of(content[1]));
        }
        return new HttpURL(path, null);
    }

    String getPath() {
        return path;
    }

    String getRequestParam(String attribute) {
        return queryString.get(attribute);
    }
}

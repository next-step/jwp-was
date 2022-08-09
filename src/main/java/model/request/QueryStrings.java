package model.request;

import utils.parser.UrlKeyValueParser;

import java.util.Map;
import java.util.regex.Pattern;

public class QueryStrings {
    private static final Pattern QUERY_STRING = Pattern.compile("^(\\/[a-zA-Z]*)*\\?([^=]+=+[^=]+)+[^=]+(=+[^=]+)?$");
    private static final String QUERYSTRING_SEPARATOR = "?";

    private final Map<String, String> queryString;

    public QueryStrings(String path) {
        if (hasQueryString(path)) {
            this.queryString = UrlKeyValueParser.parse(path);

            return;
        }

        this.queryString = null;
    }

    private Boolean hasQueryString(String path) {
        return QUERY_STRING.matcher(path).matches();
    }

    public String removeQueryString(String path) {
        if (hasQueryString(path)) {
            String queryString = path.substring(path.indexOf(QUERYSTRING_SEPARATOR));

            return path.replace(queryString, "");
        }
        return path;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public String getParameter(String key) {
        return queryString.get(key);
    }
}

package http.request;

import utils.RequestLineUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.function.Function;

public enum Path {
    URL(requestLine -> RequestLineUtils.getUrl(requestLine)),
    QUERIES(requestLine -> RequestLineUtils.getQueries(requestLine));

    private Function<String, String> expression;

    Path(Function<String, String> expression) {
        this.expression = expression;
    }

    public static String getUrl(String requestLine) {
        return URL.expression
                .apply(requestLine);
    }

    public static Map<String, String> getQueries(String requestLine) throws UnsupportedEncodingException {
        String queryStrings = QUERIES.expression
                .apply(requestLine);

        return QueryStrings.parseQueryStrings(queryStrings);
    }
}

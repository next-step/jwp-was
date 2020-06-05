package http.requestline;

import http.QueryStrings;

import java.util.Map;
import java.util.function.Function;

public enum Path2 {
    URL(requestLine -> PathUtils.getUrl(requestLine)),
    QUERIES(requestLine -> PathUtils.getQueries(requestLine));

    private Function<String, String> expression;

    Path2(Function<String, String> expression) {
        this.expression = expression;
    }

    public static String getUrl(String requestLine){
        return URL.expression
                .apply(requestLine);
    }

    public static Map<String, String> getQueries(String requestLine){
        String queryStrings = QUERIES.expression
                .apply(requestLine);
        return QueryStrings.parseQueryStrings(queryStrings);
    }
}

package http.request;

import model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QueryStrings {
    private static final String REGEX_QUERY_DELIMITER = "&";
    private static final String REGEX_KEY_VALUE_DELIMITER = "=";

    private Map<String, String> queryStrings = new HashMap<>();

    public QueryStrings(Map<String, String> queryStrings) {
        this.queryStrings = Collections.unmodifiableMap(queryStrings);
    }

    public QueryStrings(String path) throws UnsupportedEncodingException {
        Map<String, String> map = parseQueryStrings(getQueryStrings(path));
        this.queryStrings = Collections.unmodifiableMap(map);
    }

    public User toUser(){
        return new User(getParameter("userId"),
                getParameter("password"),
                getParameter("name"),
                getParameter("email"));
    }

    public String getParameter(String key){
        return this.queryStrings.get(key);
    }

    private String getQueryStrings(String path) {
        String[] splitPath = path.split("\\?");
        return splitPath[1];
    }

    public static Map<String, String> parseQueryStrings(String queryStrings) throws UnsupportedEncodingException {
        String[] splitQueryStrings = queryStrings.split(REGEX_QUERY_DELIMITER);
        Map<String, String> queries = new HashMap<>();
        for (int i = 0; i < splitQueryStrings.length; i++) {
            String[] split = splitQueryStrings[i].split(REGEX_KEY_VALUE_DELIMITER);
            String key = URLDecoder.decode(split[0], "UTF-8");
            String value = URLDecoder.decode(split[1], "UTF-8");
            queries.put(key, value);
        }
        return queries;
    }
}

package http;

import org.apache.tomcat.util.buf.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class QueryStrings {

    private Map<String, String> queryMap = new HashMap<>();

    public QueryStrings(String queryStrings) {

        Map<String, String> query_pair = new HashMap<>();
        String[] pairs = queryStrings.split("&");

        if(pairs.length > 1) {
            for(String pair : pairs) {
                String key = pair.split("=")[0];
                String value = pair.split("=")[1];
                query_pair.put(key, value);
            }
        }
        this.queryMap = query_pair;
    }

    public String getParameter(String parameter) {
        Map<String, String> query = this.queryMap;

        if(query.isEmpty())
            return "";

        String value = "";

        if(query.containsKey(parameter))
            value = query.get(parameter);

        return value;
    }
}

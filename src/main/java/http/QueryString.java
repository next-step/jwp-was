package http;

import java.util.HashMap;

public class QueryString {

    private HashMap<String, String> map = new HashMap<>();

    public QueryString(String value) {
        String[] values = value.split("&");
        for (String s : values) {
            String[] query = s.split("=");
            map.put(query[0], query[1]);
        }
    }

    public HashMap<String, String> getMap() {
        return map;
    }
}

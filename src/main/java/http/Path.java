package http;

import java.util.HashMap;

public class Path {
    private final String path;
    private final HashMap<String, String> queryString;

    public Path(final String pathStr) {
        String[] tokens = pathStr.split("\\?");

        this.path = tokens[0];
        this.queryString = new HashMap<>();

        if (tokens.length >= 2) {

        }
    }

    public String getPath() {
        return null;
    }

    public String getQueryString(String name1) {
        return null;
    }
}

package http;

import java.util.Map;
import java.util.Objects;

public class Path {
    private String path;
    private QueryStrings queryStrings;

    public Path(String path) {
        this.path = getPathOnly(path);
        this.queryStrings = new QueryStrings(path);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings.getQueryStrings();
    }

    private String getPathOnly(String path){
        String[] split = path.split("\\?");
        return split[0];
    }
}

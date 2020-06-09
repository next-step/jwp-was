package http;

public class PathAndString {
    private final String path;
    private final String queryStrings;

    public PathAndString(String path, String queryStrings) {
        this.path = path;
        this.queryStrings = queryStrings;
    }

    public String getPath() {
        return path;
    }

    public String getQueryStrings() {
        return queryStrings;
    }

    public static PathAndString splitPath(String value) {
        String[] pathAndQueryStrings = value.split("\\?");

        if(pathAndQueryStrings.length > 1) return new PathAndString(pathAndQueryStrings[0], pathAndQueryStrings[1]);

        return new PathAndString(pathAndQueryStrings[0], "");
    }
}

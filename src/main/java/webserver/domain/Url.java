package webserver.domain;

public class Url {
    public static final int PATH_INDEX = 0;
    public static final int QUERY_PARAMETER_INDEX = 1;

    private final String path;
    private final QueryParameter queryParameter;

    public Url(String path, QueryParameter queryParameter) {
        this.path = path;
        this.queryParameter = queryParameter;
    }

    public String getPath() {
        return path;
    }

    public QueryParameter getQueryParameter() {
        return queryParameter;
    }
}

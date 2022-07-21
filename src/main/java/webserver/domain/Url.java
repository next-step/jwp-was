package webserver.domain;

public class Url {
    private static final String URL_DELIMITER = "\\?";
    public static final int PATH_INDEX = 0;
    public static final int QUERY_PARAMETER_INDEX = 1;

    private final String path;
    private final QueryParameter queryParameter;

    public Url(String path, QueryParameter queryParameter) {
        this.path = path;
        this.queryParameter = queryParameter;
    }

    public static Url from(final String url) {
        String[] splitUrl = url.split(URL_DELIMITER);

        return new Url(
                splitUrl[Url.PATH_INDEX],
                QueryParameter.parseFrom(splitUrl)
        );
    }

    public String getPath() {
        return path;
    }

    public QueryParameter getQueryParameter() {
        return queryParameter;
    }
}

package webserver.domain;

public class UrlInfo {
    private static final String PATH_DELIMITER = "\\?";
    private final String path;
    private Parameters parameters;

    public UrlInfo(String url) {
        String[] split = url.split(PATH_DELIMITER);

        this.path = split[0];

        if (split.length > 1) {
            this.parameters = new Parameters(split[1]);
        }
    }

    public String getPath() {
        return path;
    }

    public Parameters getParameters() {
        return parameters;
    }
}

package request;

/**
 * Created by youngjae.havi on 2019-08-01
 */
public enum UriSplitter {
    START_QUERY("\\?"),
    SPLIT_QUERY("&"),
    SPLIT_KEY_VALUE("="),
    COMMA(",");

    private String splitter;

    UriSplitter(String splitter) {
        this.splitter = splitter;
    }

    public String getSplitter() {
        return splitter;
    }
}

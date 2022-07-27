package webserver.response;

public class ResponseHeaderWriter {
    private static final String BLANK = " ";
    private static final String LINE_CHANGE = "\r\n";
    private static final String CONTENT_LENGTH = "Content-Length:";
    private static final String CONTENT_TYPE = "Content-Type:";
    private static final String LOCATION = "Location:";
    private static final String HTTP = "http://";
    private static final String INDEX_HTML = "/index.html";
    private static final String TEXT_HTML = "text/html";
    private static final String CONTENT_TYPE_DELIMITER = ";";
    private static final String CHARSET_UTF_8 = "charset=utf-8";

    public String response200(String protocolVersion, int lengthOfBodyContent) {
        return protocolVersion + BLANK + ResponseStatus.SUCCESS.toResponseHeader() +
                LINE_CHANGE +
                CONTENT_TYPE + BLANK + TEXT_HTML + CONTENT_TYPE_DELIMITER + CHARSET_UTF_8 +
                LINE_CHANGE +
                CONTENT_LENGTH + BLANK + lengthOfBodyContent +
                LINE_CHANGE + LINE_CHANGE;
    }

    public String response302(String protocolVersion, String host) {
        return protocolVersion + BLANK + ResponseStatus.FOUND.toResponseHeader() +
                LINE_CHANGE +
                CONTENT_TYPE + BLANK + TEXT_HTML + CONTENT_TYPE_DELIMITER + CHARSET_UTF_8 +
                LINE_CHANGE +
                LOCATION + BLANK + HTTP + host + INDEX_HTML +
                LINE_CHANGE + LINE_CHANGE;
    }
}

package webserver.converter;

public abstract class HttpConverter {

    public static final String SEPARATOR = " ";
    public static final String QUERY_PREFIX = "\\?";
    public static final String QUERY_DELIMITER = "&";
    public static final String QUERY_KEY_VALUE_DELIMITER = "=";
    public static final String QUERY_NEW_LINE = "\\r\\n";
    public static final String QUERY_HEADER_KEY_VALUE = ":";
    public static final String QUERY_COOKIE_SEPARATOR = ";";

    public static final String HTML_FILE_NAMING = ".html";
    public static final String CSS_FILE_NAMING = ".css";
    public static final String ICO_FILE_NAMING = ".ico";
    public static final String JS_FILE_NAMING = ".js";

    public static final String BASIC_URL = "http://localhost:8080";
    public static final String SESSION_ID = "jsessionid";
    public static final String COOKIE = "Cookie";

}

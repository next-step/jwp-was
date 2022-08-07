package webserver.http;

public class HeaderValue {
    public static final String TEXT_HTML_UTF8 = "text/html;charset=utf-8";
    public static final String TEXT_CSS_UTF8 = "text/css;charset=utf-8";
    public static final String LOGINED_TRUE_ALL_PATH = "logined=true; Path=/";
    public static final String LOGINED_FALSE_ALL_PATH = "logined=false; Path=/";
    public static final String KEEP_ALIVE = "keep-alive";
    public static final String APPLICATION_HTML_FORM = "application/x-www-form-urlencoded";
    public static final String ALL_MIME_TYPE = "*/*";

    private HeaderValue() {
    }
}

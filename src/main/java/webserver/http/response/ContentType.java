package webserver.http.response;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public enum ContentType {
    HTML_UTF_8("text/html;charset=utf-8"),
    CSS("text/css"),
    JS("text/js"),
    ;

    private final String mediaType;

    ContentType(String mediaType) {
        this.mediaType = mediaType;
    }

    public static String getHeaderFieldName() {
        return "Content-Type";
    }

    public String getMediaType() {
        return mediaType;
    }
}

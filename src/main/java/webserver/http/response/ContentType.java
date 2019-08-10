package webserver.http.response;

import webserver.http.common.header.Header;

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
        return Header.CONTENT_TYPE.getName();
    }

    public String getMediaType() {
        return mediaType;
    }
}

package webserver.http.response;

import webserver.http.header.HttpStaticResourceFileExtension;

import java.util.Arrays;

import static webserver.http.header.HttpHeaderConstants.TEXT_CSS_CHARSET_UTF_8;
import static webserver.http.header.HttpHeaderConstants.TEXT_HTML_CHARSET_UTF_8;
import static webserver.http.header.HttpStaticResourceFileExtension.*;

public enum HttpContentTypeHeader {
    HTML_CONTENT_TYPE(HTML, TEXT_HTML_CHARSET_UTF_8),
    CSS_CONTENT_TYPE(CSS, TEXT_CSS_CHARSET_UTF_8),
    NONE_CONTENT_TYPE(NONE, "");

    private final HttpStaticResourceFileExtension extension;
    private final String contentType;

    HttpContentTypeHeader(HttpStaticResourceFileExtension extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static HttpContentTypeHeader of(HttpStaticResourceFileExtension extension) {
        return Arrays.stream(values()).filter(httpContentTypeHeader -> httpContentTypeHeader.extension == extension)
                .findAny().orElse(NONE_CONTENT_TYPE);
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isNotNoneContentType() {
        return this != NONE_CONTENT_TYPE;
    }
}

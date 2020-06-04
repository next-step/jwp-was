package webserver.mapper;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ContentType {
    TEXT("text", "text/plain"),
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "text/javascript"),
    IMAGE_PNG("png", "image/png");

    private final String fileSuffix;
    private final String contentTypeStr;

    ContentType(final String fileSuffix, final String contentTypeStr) {
        this.fileSuffix = fileSuffix;
        this.contentTypeStr = contentTypeStr;
    }

    private static final Map<String, String> ContentTypes =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ContentType::getFileSuffix, ContentType::getContentTypeStr));

    private String getFileSuffix() {
        return fileSuffix;
    }

    private String getContentTypeStr() {
        return contentTypeStr;
    }
}

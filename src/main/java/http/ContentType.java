package http;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
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

    private static final Map<String, ContentType> CONTENT_TYPES =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ContentType::getFileSuffix, Function.identity()));

    public static ContentType of(final String extension) {
        return CONTENT_TYPES.getOrDefault(extension, ContentType.TEXT);
    }

    public String getContentTypeStr() {
        return contentTypeStr;
    }

    private String getFileSuffix() {
        return fileSuffix;
    }
}

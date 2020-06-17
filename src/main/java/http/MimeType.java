package http;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.StringConstant.SEMI_COLON;
import static utils.StringConstant.SLASH;

public enum MimeType {
    ALL("*", "*"),
    APPLICATION_JSON("application", "json"),
    APPLICATION_JAVASCRIPT("application", "javascript"),
    TEXT_CSS("text", "css", Collections.singletonMap("charset", "utf-8")),
    TEXT_HTML("text", "html", Collections.singletonMap("charset", "utf-8")),
    IMAGE_GIF("image", "gif"),
    IMAGE_JPEG("image", "jpeg"),
    ;

    private final String type;
    private final String subType;
    private final Map<String, String> parameterMap;

    MimeType(String type, String subType) {
        this.type = type;
        this.subType = subType;
        this.parameterMap = Collections.EMPTY_MAP;
    }

    MimeType(String type, String subType, Map<String, String> parameterMap) {
        this.type = type;
        this.subType = subType;
        this.parameterMap = parameterMap;
    }

    String makeContentTypeValue() {
        String typeAndSubType = type + SLASH + subType + SEMI_COLON;
        if (parameterMap.isEmpty()) {
            return typeAndSubType;
        }

        String subTypeValue = parameterMap.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue() + ";")
                .collect(Collectors.joining());

        return typeAndSubType + subTypeValue;
    }
}

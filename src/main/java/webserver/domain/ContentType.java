package webserver.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum ContentType {
    JS("application/javascript;charset=utf-8", "./static"),
    HTML("text/html;charset=utf-8", "./templates"),
    CSS("text/css", "./static"),
    SVG("image/svg+xml", "./static"),
    TTF("application/octet-stream", "./static"),
    OTF("application/x-font-opentype", "./static"),
    WOFF("application/font-woff", "./static"),
    WOFF2("application/font-woff2", "./static"),
    ICO("image/avif", "./templates"),
    JSON("application/json", "");

    private static final Map<String, ContentType> contentTypeMap;
    private static final List<ContentType> resourceTypes;
    private final String value;
    private final String prefix;

    static {
        contentTypeMap = Arrays.stream(values())
                .collect(Collectors.toMap(t -> t.name().toLowerCase(), t -> t));

        resourceTypes = Arrays.asList(JS, CSS, SVG, TTF, OTF, WOFF, WOFF2, ICO);
    }

    ContentType(String value, String prefix) {
        this.value = value;
        this.prefix = prefix;
    }

    public static boolean isResourceContent(String path) {
        return resourceTypes.contains(suffixOf(path));
    }

    public String getContentType() {
        return value;
    }

    public static ContentType suffixOf(String path) {
        int index = path.lastIndexOf(".");
        if (index < 0) {
            return JSON;
        }

        String ext = path.substring(index + 1);
        return contentTypeMap.getOrDefault(ext, HTML);
    }

    public String prefix() {
        return prefix;
    }
}

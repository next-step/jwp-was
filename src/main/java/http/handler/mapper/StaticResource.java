package http.handler.mapper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.util.Arrays;
import java.util.Map;

import static http.handler.AbstractHandler.STATIC_PATH;
import static http.handler.AbstractHandler.TEMPLATE_PATH;
import static java.util.stream.Collectors.toMap;

@Slf4j
public enum StaticResource {
    HTML(".html", "text/html;charset=utf-8", TEMPLATE_PATH),
    ICON(".ico", "image/x-icon", TEMPLATE_PATH),
    JS(".js", "text/html;charset=utf-8", STATIC_PATH),
    CSS(".css", "text/css;charset=utf-8", STATIC_PATH),
    TTF(".ttf", "application/x-font-ttf", STATIC_PATH),
    WOFF(".woff", "text/html;charset=utf-8", STATIC_PATH),
    WOFF2(".woff2", "font/woff2", STATIC_PATH),
    ;

    private static final Map<String, StaticResource> staticResourceMap = initMap();
    @Getter
    private String contentType;
    @Getter
    private String suffix;
    @Getter
    private String basePath;

    StaticResource(String suffix, String contentType, String basePath) {
        this.suffix = suffix;
        this.contentType = contentType;
        this.basePath = basePath;
    }

    private static Map<String, StaticResource> initMap() {
        return Arrays.stream(values())
            .collect(toMap(StaticResource::getSuffix, entry -> entry));
    }

    public static boolean matches(String url) {
        String fileExtension = getFileExtension(url);

        if (StringUtils.isEmpty(fileExtension)) {
            return false;
        }

        return staticResourceMap.containsKey(fileExtension);
    }

    public static StaticResource getStaticResource(String url) {
        return staticResourceMap.get(getFileExtension(url));
    }

    private static String getFileExtension(String url) {
        int beginIndex = url.lastIndexOf(".");

        if (beginIndex < 0) {
            return "";
        }

        return url.substring(beginIndex);
    }
}

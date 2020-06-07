package http.request.mapper;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static http.request.mapper.StaticResources.*;
import static java.util.stream.Collectors.toMap;

public enum StaticResourceMapper {
    HTML(HTML_SUFFIX, HTML_PATH),
    JPG(JPG_SUFFIX, IMAGE_PATH),
    PNG(PNG_SUFFIX, IMAGE_PATH),
    GIF(GIF_SUFFIX, IMAGE_PATH),
    JS(JS_SUFFIX, JS_PATH),
    CSS(CSS_SUFFIX, CSS_PATH),
    EOT(EOT_SUFFIX, FONT_PATH),
    SVG(SVG_SUFFIX, FONT_PATH),
    TTF(TTF_SUFFIX, FONT_PATH),
    WOFF(WOFF_SUFFIX, FONT_PATH),
    WOFF2(WOFF2_SUFFIX, FONT_PATH),
    ICON(ICON_SUFFIX, ICON_PATH),
    ;

    @Getter
    private String suffix;

    @Getter
    private String path;

    private static final Map<String, String> staticResourcesPathMap = initMap();

    private static Map<String, String> initMap() {
        return Arrays.stream(values())
            .collect(toMap(StaticResourceMapper::getSuffix, StaticResourceMapper::getPath));
    }

    StaticResourceMapper(String suffix, String path) {
        this.suffix = suffix;
        this.path = path;
    }

    public static String mapStaticResources(String url) {
        return staticResourcesPathMap.get(url);
    }
}

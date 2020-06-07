package http.request.mapper;

import http.controller.Controller;
import http.controller.StaticResourceController;
import http.exception.BadRequestException;
import http.exception.NotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

import static http.request.mapper.StaticResources.*;
import static java.util.stream.Collectors.toMap;

@Slf4j
public enum StaticResourceMapper {
    HTML(TEMPLATE_PREFIX, HTML_SUFFIX, ROOT_PATH),
    JPG(STATIC_PREFIX, JPG_SUFFIX, IMAGE_PATH),
    PNG(STATIC_PREFIX, PNG_SUFFIX, IMAGE_PATH),
    GIF(STATIC_PREFIX, GIF_SUFFIX, IMAGE_PATH),
    JS(STATIC_PREFIX, JS_SUFFIX, JS_PATH),
    CSS(STATIC_PREFIX, CSS_SUFFIX, CSS_PATH),
    EOT(STATIC_PREFIX, EOT_SUFFIX, FONT_PATH),
    SVG(STATIC_PREFIX, SVG_SUFFIX, FONT_PATH),
    TTF(STATIC_PREFIX, TTF_SUFFIX, FONT_PATH),
    WOFF(STATIC_PREFIX, WOFF_SUFFIX, FONT_PATH),
    WOFF2(STATIC_PREFIX, WOFF2_SUFFIX, FONT_PATH),
    ICON(TEMPLATE_PREFIX, ICON_SUFFIX, ROOT_PATH),
    ;

    @Getter
    private String prefix;

    @Getter
    private String suffix;

    @Getter
    private String path;

    private static final Map<String, StaticResourceMapper> staticResourcesPathMap = initMap();

    private static Map<String, StaticResourceMapper> initMap() {
        return Arrays.stream(values())
            .collect(toMap(StaticResourceMapper::getSuffix, entry -> entry));
    }

    StaticResourceMapper(String prefix, String suffix, String path) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.path = path;
    }

    public static boolean isStaticResource(String url) {
        return staticResourcesPathMap.containsKey(getFileExtension(url));
    }

    private static String getFileExtension(String url) {
        return url.substring(url.lastIndexOf("."));
    }

    public static Controller getStaticResourceController(String url) {
        if (!isStaticResource(url)) {
            throw new NotFoundException();
        }

        return new StaticResourceController(staticResourcesPathMap.get(getFileExtension(url)));
    }
}

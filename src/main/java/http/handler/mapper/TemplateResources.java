package http.handler.mapper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.util.Arrays;

import static java.util.stream.Collectors.toMap;

@Slf4j
public enum TemplateResources {
    HTML("text/html;charset=utf-8", ".html"),
    ICON("image/x-icon", ".ico"),
    ;

    @Getter
    private String contentType;

    @Getter
    private String suffix;

    TemplateResources(String contentType, String suffix) {
        this.contentType = contentType;
        this.suffix = suffix;
    }

    public static boolean matches(String url) {
        if (StringUtils.isEmpty(url) || "/".equals(url)) {
            return true;
        }

        return containsEndsWithSuffix(url);
    }

    private static boolean containsEndsWithSuffix(String url) {
        return Arrays.stream(values())
                .anyMatch(resource -> endsWithSuffix(url, resource.getSuffix()));
    }

    public static String getContentType(String url) {
        return Arrays.stream(values())
            .filter(resource -> endsWithSuffix(url, resource.getSuffix()))
            .findFirst()
            .map(TemplateResources::getContentType)
            .orElse("");
    }

    private static boolean endsWithSuffix(String url, String suffix) {
        return url.toLowerCase().endsWith(suffix);
    }
}

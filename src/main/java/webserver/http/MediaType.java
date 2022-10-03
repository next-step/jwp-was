package webserver.http;

import exception.NotFoundMediaTypeException;

import java.util.Arrays;

public enum MediaType {
    CSS("text/css"),
    JS("text/javascript"),
    HTML("text/html;charset=utf-8"),

    PNG("image/png"),
    JPEG("image/jpeg"),
    SVG("image/svg+xml"),
    ICO("image/x-icon"),

    EOT("application/vnd.ms-fontobject"),
    TTF("application/x-font-ttf"),
    WOFF("application/x-font-woff"),
    WOFF2("application/x-font-woff");

    private final String chemical;

    MediaType(String chemical) {
        this.chemical = chemical;
    }

    public static MediaType from(String extension) {
        return Arrays.stream(values())
                .filter(mediaType -> mediaType.name().equalsIgnoreCase(extension))
                .findFirst()
                .orElseThrow(() -> new NotFoundMediaTypeException(extension));
    }

    public boolean isHtml() {
        return this == HTML;
    }

    public boolean isIco() {
        return this == ICO;
    }

    public String getChemical() {
        return chemical;
    }
}

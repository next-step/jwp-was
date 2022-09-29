package webserver.http;

import java.util.Arrays;
import java.util.Optional;

public enum MediaType {
    CSS("text/css"),
    JS("text/javascript"),

    PNG("image/png"),
    JPEG("image/jpeg"),
    SVG("image/svg+xml"),

    EOT("application/vnd.ms-fontobject"),
    TTF("application/x-font-ttf"),
    WOFF("application/x-font-woff"),
    WOFF2("application/x-font-woff");

    private final String chemical;

    MediaType(String chemical) {
        this.chemical = chemical;
    }

    public static Optional<MediaType> from(String extension) {
        return Arrays.stream(values())
                .filter(mediaType -> mediaType.name().equalsIgnoreCase(extension))
                .findFirst();
    }

    public String getChemical() {
        return chemical;
    }
}

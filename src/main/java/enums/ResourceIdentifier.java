package enums;

import java.util.Arrays;

public enum ResourceIdentifier {
    CSS(".css"),
    JS(".js"),
    PNG(".png"),
    HTML(".html"),
    ICO(".ico"),
    XML(".xml"),
    TTF(".ttf"),
    WOFF(".woff"),
    WOFF2(".woff2"),
    SVG(".svg"),
    EOT(".eot");

    private final String identifier;

    ResourceIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public static boolean anyMatch(String input) {
        return Arrays.stream(values()).anyMatch(it ->
            input.contains(it.identifier)
        );
    }
}

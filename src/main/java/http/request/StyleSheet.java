package http.request;

import java.util.Arrays;

public enum StyleSheet {
    CSS("/css", "css"),
    JS("/js", "js"),
    FONT("/fonts", "ttf"),
    FONT2("/fonts", "woff");

    private final String location;
    private final String extension;

    StyleSheet(String location, String extension) {
        this.location = location;
        this.extension = extension;
    }

    public static boolean isContain(String value) {
        return Arrays.stream(StyleSheet.values())
                .map(StyleSheet::getLocation)
                .anyMatch(value::contains);
    }

    public static StyleSheet findByExtension(String extension) {
        return Arrays.stream(StyleSheet.values())
                .filter(s -> extension.equals(s.extension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 extension =" + extension));
    }

    public String getLocation() {
        return location;
    }

    public String getExtension() {
        return extension;
    }
}

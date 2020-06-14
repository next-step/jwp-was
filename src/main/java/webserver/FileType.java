package webserver;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;

import java.util.Set;

@Getter
public enum FileType {

    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("javascript", "text/js"),
    ICO("ico", "image/ico"),
    GIF("gif", "image/gif"),
    PNG("png", "image/png"),
    WOFF("woff", "font/woff"),
    TTF("ttf", "font/ttf"),
    NONE("not specified file type");

    private static Set<FileType> templates = ImmutableSet.of(HTML, ICO);
    private static Set<FileType> statics = ImmutableSet.of(CSS, JS, GIF, PNG, WOFF, TTF);

    private String description;
    private String contentType;

    FileType(String description) {
        this.description = description;
    }

    FileType(String description, String contentType) {
        this.description = description;
        this.contentType = contentType;
    }

    public String getLocation() {
        if (templates.contains(this)) {
            return "./templates";
        }
        return "./static";
    }
}

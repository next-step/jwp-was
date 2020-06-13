package http;

import java.util.Arrays;

public enum FileExtension {
    NONE("", ""),
    JS("application/js", "./static"),
    CSS("text/css", "./static"),
    TTF("application/x-font-ttf", "./static"),
    WOFF("font/woff", "./static"),
    PNG("image/png", "./static"),
    JPEG("image/jpeg", "./static"),
    JPG("image/png", "./static"),
    HTML("text/html", "./templates"),
    ICO("image/x-icon", "./templates");

    private String mimeType;

    private String physicalPath;

    public String getMimeType() {
        return mimeType;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    FileExtension(String mimeType, String physicalPath) {
        this.mimeType = mimeType;
        this.physicalPath = physicalPath;
    }

    public static FileExtension of(String extension) {
        return Arrays.stream(FileExtension.values())
                .filter(f -> f.toString().equals(extension.toUpperCase()))
                .findFirst().orElse(NONE);
    }
}

package http;

import java.util.Arrays;
import java.util.stream.Stream;

public enum ContentType {

    css("text/css", "css", "/css", "./static"),
    html("text/html", "html", "", "./templates"),
    woff("application/x-font-woff", "woff", "/fonts", "./static"),
    ttf("application/x-font-ttf", "ttf", "/fonts", "./static"),
    js("application/javascript", "js", "/js", "./static");

    String mimeType;
    String url;
    String resourceFolderName;
    String resourcePath;

    ContentType(String mimeType, String url, String resourceFolderName, String resourcePath) {
        this.mimeType = mimeType;
        this.url = url;
        this.resourceFolderName = resourceFolderName;
        this.resourcePath = resourcePath;
    }

    public static ContentType getContentType(String path) {
        return Arrays.stream(ContentType.values())
                .filter(v -> path.endsWith(v.url))
                .findAny()
                .orElse(html);
    }


    public String getMimeType() {
        return mimeType;
    }

    public String getResourcePath() {
        return resourcePath;
    }

}

package webserver.http.response.header;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html;charset=utf-8", ".html", "./templates"),
    CSS("text/css", ".css", "./static"),
    JS("application/javascript;charset=utf-8", ".js", "./static"),
    ICO("image/avif", ".ico", "./templates");

    private final String content;
    private final String fileExtension;
    private final String filePath;

    ContentType(String content, String fileExtension, String filePath) {
        this.content = content;
        this.fileExtension = fileExtension;
        this.filePath = filePath;
    }

    public static ContentType response(String uri) {
        return Arrays.stream(values())
                .filter(contentType -> uri.endsWith(contentType.fileExtension))
                .findAny()
                .orElse(HTML);
    }

    public static String filePath(String uri) {
        return response(uri).filePath + uri;
    }

    public String getContent() {
        return content;
    }
}

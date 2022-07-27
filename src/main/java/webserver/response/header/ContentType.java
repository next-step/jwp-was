package webserver.response.header;

public enum ContentType {
    HTML("text/html;charset=utf-8", "./templates"),
    CSS("text/css", "./static"),
    JS("application/javascript;charset=utf-8", "./static"),
    ICO( "image/avif", "./templates");

    private final String content;
    private final String filePath;

    ContentType(String content, String filePath) {
        this.content = content;
        this.filePath = filePath;
    }

    public static ContentType response(String index) {
        if (index.endsWith(".html")) {
            return HTML;
        }
        if (index.endsWith(".css")) {
            return CSS;
        }
        if (index.endsWith(".js")) {
            return JS;
        }
        if (index.endsWith(".ico")) {
            return ICO;
        }
        return HTML;
    }

    public static String filePath(String index) {
        return response(index).filePath;
    }

    public String getContent() {
        return content;
    }
}

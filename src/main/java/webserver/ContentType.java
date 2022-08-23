package webserver;

public enum ContentType {

    HTML("text/html;charset=utf-8", "./templates"),
    CSS("text/css", "./static"),
    JS("application/javascript;charset=utf-8", "./static");

    private final String content;
    private final String filePath;

    ContentType(String content, String filePath) {
        this.content = content;
        this.filePath = filePath;
    }

    public String getContent() {
        return content;
    }

    public static String selectContent(String fileExtension) {
        if (fileExtension.equals("html")) {
            return HTML.content;
        } else if (fileExtension.equals("css")) {
            return CSS.content;
        } else if (fileExtension.equals("js")) {
            return JS.content;
        }
        return HTML.content;
    }

}

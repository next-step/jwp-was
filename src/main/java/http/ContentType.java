package http;

public enum ContentType {
    CSS("text/css", "/css", "./static"),
    HTML("text/html", "", "./templates"),
    FONT("application/x-font-woff", "/fonts", "./static"),
    JS("application/javascript","/js", "./static");

    String mimeType;
    String resourceFolderName;
    String resourcePath;

    ContentType(String mimeType, String resourceFolderName, String resourcePath) {
        this.mimeType = mimeType;
        this.resourceFolderName = resourceFolderName;
        this.resourcePath = resourcePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}

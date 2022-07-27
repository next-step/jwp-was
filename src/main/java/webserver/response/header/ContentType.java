package webserver.response.header;

public enum ContentType {
    HTML("text/html;charset=utf-8");

    final String content;

    ContentType(String content) {
        this.content = content;
    }
}

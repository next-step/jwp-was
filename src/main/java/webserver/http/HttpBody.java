package webserver.http;

public class HttpBody {
    private Integer body;
    private Contents contents;

    public HttpBody(String body) {
        this.contents = Contents.from(body);
    }

    public Contents getContents() {
        return contents;
    }
}

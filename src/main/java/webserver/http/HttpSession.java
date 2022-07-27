package webserver.http;

public class HttpSession {

    private final String id;

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

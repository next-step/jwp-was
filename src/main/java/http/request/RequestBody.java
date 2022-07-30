package http.request;

public class RequestBody {

    private final String value;

    public RequestBody(String body) {
        this.value = body;
    }

    public String getValue() {
        return value;
    }
}

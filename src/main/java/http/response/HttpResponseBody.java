package http.response;

public class HttpResponseBody {

    private final String value;

    public HttpResponseBody(String body) {
        this.value = body;
    }

    public String getValue() {
        return value;
    }

    public int length() {
        return value.length();
    }
}

package http;

public class RequestBody {

    private final String requestBody;

    public RequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String value() {
        return this.requestBody;
    }
}

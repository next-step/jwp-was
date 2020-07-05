package http;

public class RequestBody {

    private static final String EMPTY = "";

    private final String requestBody;

    public RequestBody() {
        requestBody = EMPTY;
    }

    public RequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "requestBody='" + requestBody + '\'' +
                '}';
    }
}

package http;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private static final String EMPTY = "";

    private final String requestBody;

    public RequestBody() {
        requestBody = EMPTY;
    }

    public RequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, String> getRequestBody() {
        Map<String, String> body = new HashMap<>();
        String[] tokens = requestBody.split("&");
        for (final String token : tokens) {
            String[] split = token.split("=");
            body.put(split[0], split[1]);
        }
        return body;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "requestBody='" + requestBody + '\'' +
                '}';
    }
}

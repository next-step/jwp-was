package http;

import static http.RequestConstant.*;

public class RequestLineGet extends RequestLine {

    public RequestLineGet(String[] tokens) {
        super(Method.GET, tokens[PATH_INDEX], tokens[PROTOCOL_INDEX]);
    }
}

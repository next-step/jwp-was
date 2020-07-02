package http;

import static http.RequestConstant.*;

public class RequestLineParser {
    private static final String BLANK = " ";

    public static RequestLine parse(String requestLine) {
        String[] tokens = requestLine.split(BLANK);
        Method method = Method.valueOf(tokens[METHOD_INDEX]);
        return new RequestLine(method, tokens[PATH_INDEX], tokens[PROTOCOL_INDEX]);
    }
}

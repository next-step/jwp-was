package http;

import static http.RequestConstant.METHOD_INDEX;

public class RequestLineParser {
    private static final String BLANK = " ";

    public static RequestLine parse(String requestLine) {
        String[] tokens = requestLine.split(BLANK);
        Method method = Method.valueOf(tokens[METHOD_INDEX]);

        System.out.println(method.toString());

        if (method.equals(Method.GET)) {
            return new RequestLineGet(tokens);
        }

        if (method.equals(Method.POST)) {
            return new RequestLinePost(tokens);
        }

        throw new IllegalArgumentException("Invalid Method");
    }
}

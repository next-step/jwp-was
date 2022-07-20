package model.request;

import webserver.http.Method;

public class RequestLine {

    private static final String DELIMITER = " ";
    private static final int INDEX_METHOD = 0;
    private final Method method;

    public RequestLine(Method method) {
        this.method = method;
    }

    public static RequestLine parse(String request) {
        String[] requestElements = request.split(DELIMITER);

        Method method = Method.from(requestElements[INDEX_METHOD]);

        return new RequestLine(method);
    }

    public Method getMethod() {
        return method;
    }
}

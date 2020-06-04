package http;

import http.enums.Method;

public class RequestLineParser {
    public static RequestLine parse(String s) {
        String[] values = s.split(" ");
        return new RequestLine(Method.valueOf(values[0]),values[1], new Protocol(values[2]));
    }
}

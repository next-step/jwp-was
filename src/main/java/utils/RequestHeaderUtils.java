package utils;

import http.RequestHeaderFirstLine;

public class RequestHeaderUtils {
    public static RequestHeaderFirstLine parse(String requestHeaderFirstLine) {
        String[] values = requestHeaderFirstLine.split(" ");
        return new RequestHeaderFirstLine(values[0], values[1], values[2]);
    }
}

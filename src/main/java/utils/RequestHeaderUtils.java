package utils;

import http.HttpMethod;
import http.RequestHeaderFirstLine;

public class RequestHeaderUtils {
    public static RequestHeaderFirstLine parse(String requestHeaderFirstLine) {
        String[] values = requestHeaderFirstLine.split(" ");
        return new RequestHeaderFirstLine(HttpMethod.valueOf(values[0]), values[1], values[2]);
    }
}

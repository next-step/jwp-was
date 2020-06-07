package utils;

public class RequestHeaderUtils {
    public static String parser(String requestHeaderFirstLine) {
        String[] values = requestHeaderFirstLine.split(" ");
        return values[1];
    }
}

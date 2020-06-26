package http;

public class RequestHeadersParser {

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static RequestHeader parse(String readLine) {
        String[] tokens = readLine.split(":");
        return new RequestHeader(tokens[KEY_INDEX], tokens[VALUE_INDEX]);
    }
}

package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final String END_OF_LINE = "";
    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_PAIR_COUNT = 2;

    private RequestLine requestLine;
    private Map<String, String> headers;

    private HttpRequest(RequestLine requestLine, Map<String, String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        RequestLine requestLine = RequestLine.parse(line);

        Map<String, String> headers = new HashMap<>();

        while (!line.equals(END_OF_LINE)) {
            line = bufferedReader.readLine();
            String[] values = line.split(HEADER_DELIMITER);

            if(hasValues(values)) {
                headers.put(values[0], values[1]);
            }
        }

        return new HttpRequest(requestLine, headers);
    }

    private static boolean hasValues(String[] values) {

        return values.length == HEADER_PAIR_COUNT;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                '}';
    }
}

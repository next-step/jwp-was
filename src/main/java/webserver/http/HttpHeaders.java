package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private static final String DELIMITER = ": ";
    private static final int HEADER_PAIR_LIMIT = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private Map<String, String> headers;

    private HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders create() {
        return new HttpHeaders(new HashMap<>());
    }

    public HttpHeaders(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        while (!line.isEmpty()) {
            addHeader(line);
            line = bufferedReader.readLine();
        }
    }

    public static HttpHeaders redirect(String redirectUrl) {
        Map<String, String> headers = new HashMap<>();
        final String location = "http://localhost:8080";
        headers.put(HttpHeader.LOCATION, location + redirectUrl);

        return new HttpHeaders(headers);
    }


    public void addHeader(String headerLine) {
        if (headerLine == null) {
            throw new IllegalArgumentException();
        }

        String[] headerPair = headerLine.split(DELIMITER);

        if (headerPair.length == HEADER_PAIR_LIMIT) {
            headers.put(headerPair[KEY], headerPair[VALUE].trim());
        }
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get(HttpHeader.CONTENT_LENGTH));
    }

    public boolean hasContent() {
        return headers.containsKey(HttpHeader.CONTENT_LENGTH);
    }

    public boolean hasLocation() {
        return headers.containsKey(HttpHeader.LOCATION);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }
}

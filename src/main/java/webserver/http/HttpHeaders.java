package webserver.http;

import exception.NotFoundHttpHeaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private static final String DELIMITER = ": ";
    private static final int HEADER_PAIR_LIMIT = 2;
    private static final String HEADER_CONTENT_LENGTH = "Content-Length";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> headers = new HashMap<>();

    public HttpHeaders() {
    }

    public HttpHeaders(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        while (!line.isEmpty()) {
            addHeader(line);
            line = bufferedReader.readLine();
        }
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
        if (containsContentLength()) {
            return Integer.parseInt(headers.get(HEADER_CONTENT_LENGTH));
        }
        throw new NotFoundHttpHeaderException(HEADER_CONTENT_LENGTH);
    }

    private boolean containsContentLength() {
        return headers.containsKey(HEADER_CONTENT_LENGTH);
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

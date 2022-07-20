package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpHeaders  {

    private static final String BLANK = "";
    private static final String DELIMITER_FOR_HEADER = ": ";
    private static final int INDEX_HEADER_NAME = 0;
    private static final int INDEX_HEADER_VALUE = 1;
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders from(BufferedReader reader) throws IOException {
        Map<String, String> headers = new LinkedHashMap<>();
        String line = reader.readLine();
        while (line != null && !BLANK.equals(line)) {
            String[] tokens = line.split(DELIMITER_FOR_HEADER);
            headers.put(tokens[INDEX_HEADER_NAME], tokens[INDEX_HEADER_VALUE]);
            line = reader.readLine();
        }
        return new HttpHeaders(headers);
    }

    public void add(String name, String value) {
        headers.put(name, value);
    }

    public String get(String name) {
        return headers.get(name);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH, "0"));
    }
}

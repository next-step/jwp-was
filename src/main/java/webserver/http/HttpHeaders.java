package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpHeaders  {

    private static final Logger logger = LoggerFactory.getLogger(HttpHeaders.class);
    private static final String BLANK = "";
    private static final String DELIMITER_COLON = ": ";
    private static final String DELIMITER_COMMA = ",";
    private static final String COOKIE = "Cookie";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String ACCEPT = "Accept";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;

    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders from(BufferedReader reader) throws IOException {
        Map<String, String> headers = new LinkedHashMap<>();
        String line = reader.readLine();
        while (line != null && !BLANK.equals(line)) {
            logger.debug("header = {}", line);
            String[] tokens = line.split(DELIMITER_COLON);
            headers.put(tokens[INDEX_ZERO], tokens[INDEX_ONE]);
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

    public Cookies getCookies() {
        return new Cookies(get(COOKIE));
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH, "0"));
    }

    public String getAccept() {
        String accept = get(ACCEPT);
        return accept.split(DELIMITER_COMMA)[INDEX_ZERO];
    }
}

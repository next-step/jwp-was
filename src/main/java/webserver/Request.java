package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private final RequestLine requestLine;

    private final Map<String, String> headers;

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Request(final String requestLine) {
        this.requestLine = new RequestLine(requestLine);
        this.headers = new LinkedHashMap<>();
    }

    public Request(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        this.requestLine = new RequestLine(line);
        this.headers = getHeaders(bufferedReader, line);
    }

    private Map<String, String> getHeaders(BufferedReader bufferedReader, String line) throws IOException {
        Map<String, String> headers = new LinkedHashMap<>();

        while (!line.equals("")) {
            line = bufferedReader.readLine();
            logger.debug("header: {}", line);
            String[] keyValue = line.split(":");
            headers.put(keyValue[0], keyValue[1]);
        }

        return headers;
    }

    public String getContentType() {
        return headers.getOrDefault("Accept", "")
                .split(",")[0];
    }

}

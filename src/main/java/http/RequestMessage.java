package http;

import utils.RequestParseUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMessage {
    private static final String HEADER_DELIMITER = ": ";

    private final RequestLine requestLine;
    private final Map<String, String> header;

    private RequestMessage(RequestLine requestLine, Map<String, String> header) {
        this.requestLine = requestLine;
        this.header = header;
    }

    public static RequestMessage of(List<String> headers) {
        Map<String, String> parsedHeader = new HashMap<>();
        for (int i = 1; i < headers.size(); i++) {
            String[] values = RequestParseUtils.splitIntoPair(headers.get(i), HEADER_DELIMITER);
            parsedHeader.put(values[0], values[1]);
        }

        return new RequestMessage(RequestLine.from(headers.get(0)), parsedHeader);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getPath() {
        return this.requestLine.getPath();
    }
}

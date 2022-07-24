package webserver.http.request.header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeaders {
    private static String HTTP_REQUEST_HEADER_DELIMITER = ":";
    private static int HTTP_REQUEST_HEADER_SCHEMAS_MAXIMUM_SIZE = 2;
    private static int HTTP_REQUEST_HEADER_NAME_SCHEMA_INDEX = 0;
    private static int HTTP_REQUEST_HEADER_VALUE_SCHEMA_INDEX = 1;
    private static String HTTP_CONTENT_LENGTH_HEADER_NAME = "Content-Length";

    private Map<String, String> headers;

    public HttpRequestHeaders(List<String> rawHeaders) {
        this.headers = parseRequestHeaderMap(rawHeaders);
    }

    private Map<String, String> parseRequestHeaderMap(List<String> rawHeaders) {
        Map<String, String> headers = new HashMap<>();
        for (String rawHeader : rawHeaders) {
            String[] httpRequestHeaderSchemas = rawHeader.split(HTTP_REQUEST_HEADER_DELIMITER, HTTP_REQUEST_HEADER_SCHEMAS_MAXIMUM_SIZE);

            String headerName = httpRequestHeaderSchemas[HTTP_REQUEST_HEADER_NAME_SCHEMA_INDEX];
            String headerValue = httpRequestHeaderSchemas[HTTP_REQUEST_HEADER_VALUE_SCHEMA_INDEX].trim();

            headers.put(headerName, headerValue);
        }

        return headers;
    }

    public String get(String headerName) {
        return headers.get(headerName);
    }

    public int contentLength() {
        String contentLengthHeader = headers.get(HTTP_CONTENT_LENGTH_HEADER_NAME);

        if (contentLengthHeader == null || contentLengthHeader.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(contentLengthHeader);
    }
}

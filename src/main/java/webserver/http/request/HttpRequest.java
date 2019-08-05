package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-05
 */
public class HttpRequest {

    private static final String END_OF_LINE = "";
    private static final String REQUEST_HEADER_DELIMITER = ": ";
    private RequestLine requestLine;
    private Map<String, String> headers;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {

        String line = bufferedReader.readLine();
        this.requestLine = RequestLine.parse(line);

        this.headers = new HashMap<>();
        while (!(line = bufferedReader.readLine()).equals(END_OF_LINE)) {
            int indexOfHeaderKey = line.indexOf(REQUEST_HEADER_DELIMITER);
            this.headers.put(line.substring(0, indexOfHeaderKey), line.substring(indexOfHeaderKey + REQUEST_HEADER_DELIMITER.length()));
        }
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}

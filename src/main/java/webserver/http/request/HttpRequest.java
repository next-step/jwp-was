package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import utils.IOUtils;

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
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, Map<String, String> headers, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
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

        String requestBody = StringUtils.EMPTY;
        if (headers.get("Content-Length") != null) {
            requestBody = IOUtils.readData(bufferedReader, Integer.parseInt(headers.get("Content-Length")));
        }

        return new HttpRequest(requestLine, headers, RequestBody.parse(requestBody));
    }

    private static boolean hasValues(String[] values) {

        return values.length == HEADER_PAIR_COUNT;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public boolean isPost() {
        return requestLine.getMethod().equals(HttpMethod.POST);
    }

    public boolean isGet() {
        return requestLine.getMethod().equals(HttpMethod.GET);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                '}';
    }
}

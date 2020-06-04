package http;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        this.requestLine = RequestLine.from(bufferedReader.readLine());
        this.httpHeaders = HttpHeaders.from(getHeaderString(bufferedReader));
    }

    private String getHeaderString(BufferedReader bufferedReader) throws IOException {
        List<String> headerStrings = new ArrayList<>();

        String header;
        while((header = bufferedReader.readLine()) != null && !StringUtils.isEmpty(header)) {
            headerStrings.add(header);
        }

        return String.join("\n", headerStrings.toArray(new String[]{}));
    }

    public static HttpRequest from(BufferedReader bufferedReader) throws IOException {
        return new HttpRequest(bufferedReader);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }
}

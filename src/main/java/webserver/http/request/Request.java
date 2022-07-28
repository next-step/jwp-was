package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static utils.IOUtils.readData;
import static utils.IOUtils.readLines;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    public static final String ROOT_PATH = "/";
    public static final String ROOT_FILE = "/index.html";


    private final RequestLine requestLine;
    private final RequestHeader header;
    private final RequestBody requestBody;

    public Request(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<String> requests = readLines(br);
        this.requestLine = new RequestLine(requests.get(0));
        requests.remove(0);
        this.header = new RequestHeader(requests);
        this.requestBody = new RequestBody(readData(br, this.header.getContentLength()));
    }

    public Request(RequestLine requestLine, RequestHeader header, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.header = header;
        this.requestBody = requestBody;
    }

    public String getRequestPath() {
        return StringUtils.equals(requestLine.getPathWithoutQueryString(), ROOT_PATH) ? getRedirectUrl() : requestLine.getPathWithoutQueryString();
    }

    public Map<String, String> getRequestQueryString() {
        return requestLine.getQueryStringWithoutPathFromPath();
    }

    private String getRedirectUrl() {
        return ROOT_FILE;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public Map<String, String> getRequestBody() {
        return requestBody.getRequestBodyMap();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(requestLine, request.requestLine) && Objects.equals(header, request.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header);
    }
}

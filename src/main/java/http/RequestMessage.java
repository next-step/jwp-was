package http;


import utils.IOUtils;
import utils.StringUtils;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestMessage {
    public static final String REQUEST_MESSAGE_IS_INVALID = "request message is invalid.";

    private final RequestLine requestLine;
    private final Header header;
    private final String body;

    private RequestMessage(RequestLine requestLine, Header header, String body) {
        if (requestLine == null || header == null || body == null) {
            throw new IllegalArgumentException(REQUEST_MESSAGE_IS_INVALID);
        }
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static RequestMessage from(BufferedReader br) throws IOException {
        RequestLine requestLine = getRequestLine(br);
        Header header = getHeader(br);
        String body = getBody(br, requestLine, header);
        return new RequestMessage(requestLine, header, body);
    }

    private static RequestLine getRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new EOFException();
        }
        return RequestLine.from(line);
    }

    private static Header getHeader(BufferedReader br) throws IOException {
        String line;
        List<String> others = new ArrayList<>();
        while (!StringUtils.isEmpty(line = br.readLine())) {
            others.add(line);
        }
        return new Header(others);
    }

    private static String getBody(BufferedReader br, RequestLine requestLine, Header header) throws IOException {
        String body = "";
        if (requestLine.getMethod().canSupportBody()) {
            int contentLength = Integer.parseInt(header.get("Content-Length"));
            body = IOUtils.readData(br, contentLength);
        }
        return body;
    }

    public static RequestMessage createWithDefaultBody(RequestLine requestLine, Header header) {
        return new RequestMessage(requestLine, header, "");
    }

    public static RequestMessage create(RequestLine requestLine, Header header, String body) {
        return new RequestMessage(requestLine, header, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMessage that = (RequestMessage) o;
        return Objects.equals(requestLine, that.requestLine) &&
                Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header);
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public QueryString getQueryString() {
        return this.requestLine.getQueryString();
    }

    public QueryString getBody() {
        return new QueryString(body);
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public Header getHeader() {
        return this.header;
    }

    public Uri getUri() {
        return this.requestLine.getUri();
    }
}

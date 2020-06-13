package http;


import utils.StringUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestMessage {
    public static final String REQUEST_MESSAGE_IS_INVALID = "request message is invalid.";

    private final RequestLine requestLine;
    private final Header header;

    private RequestMessage(RequestLine requestLine, Header header) {
        if (requestLine == null) {
            throw new IllegalArgumentException(REQUEST_MESSAGE_IS_INVALID);
        }
        this.requestLine = requestLine;
        this.header = header;
    }

    public static RequestMessage from(BufferedReader br) throws IOException {
        String startLine = br.readLine();

        String line;
        List<String> others = new ArrayList<>();
        while (!StringUtils.isEmpty(line = br.readLine())) {
            others.add(line);
        }
        return new RequestMessage(RequestLine.from(startLine), new Header(others));
    }

    public static RequestMessage of(RequestLine requestLine, Header header) {
        return new RequestMessage(requestLine, header);
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
}

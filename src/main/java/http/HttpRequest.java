package http;

import java.io.*;
import java.util.Objects;

public class HttpRequest {

    private RequestLine requestLine;

    public HttpRequest(InputStream inputStream) throws IOException {
        String requestLine;
        do {
            requestLine = readLine(inputStream);
            if (requestLine == null) {
                return;
            }
        } while (requestLine.equals(""));

        this.requestLine = RequestLineParser.parse(requestLine);
    }

    private String readLine(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        return bufferedReader.readLine();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpRequest)) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(getRequestLine(), that.getRequestLine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequestLine());
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                '}';
    }
}

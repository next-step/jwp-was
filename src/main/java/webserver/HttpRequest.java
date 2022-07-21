package webserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequest {

    private static final int REQUEST_LINE_INDEX = 0;

    private final List<String> httpRequestMessage;

    public HttpRequest(final InputStream inputStream) {
        this.httpRequestMessage = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.toList());
    }

    public HttpRequestLine parseHttpRequestLine() {
        return new HttpRequestLine(httpRequestMessage.get(REQUEST_LINE_INDEX));
    }
}

package webserver.http;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import static java.lang.System.lineSeparator;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;

    private HttpRequest(final RequestLine requestLine,
                        final HttpHeaders httpHeaders) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
    }

    public static HttpRequest of(final InputStream in) throws IOException {
        final BufferedReader requestReader = new BufferedReader(new InputStreamReader(in));

        final RequestLine requestLine = RequestLine.parse(requestReader.readLine());
        final HttpHeaders httpHeaders = readHeaders(requestReader);

        return new HttpRequest(requestLine, httpHeaders);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean matchPath(final String regex) {
        return Pattern.matches(regex, getPath());
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    private static HttpHeaders readHeaders(final BufferedReader requestReader) throws IOException {
        final StringBuilder rawHeadersBuilder = new StringBuilder();

        String readRawHeader = requestReader.readLine();
        while (StringUtils.isNotBlank(readRawHeader)) {
            rawHeadersBuilder.append(readRawHeader).append(lineSeparator());

            readRawHeader = requestReader.readLine();
        }

        return HttpHeaders.of(rawHeadersBuilder.toString());
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", httpHeaders=" + httpHeaders +
                '}';
    }
}

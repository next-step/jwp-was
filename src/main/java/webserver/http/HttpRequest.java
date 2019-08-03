package webserver.http;

import utils.IOUtils;
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
    private final String body;

    private HttpRequest(final RequestLine requestLine,
                        final HttpHeaders httpHeaders,
                        final String body) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public static HttpRequest of(final InputStream in) throws IOException {
        final BufferedReader requestReader = new BufferedReader(new InputStreamReader(in));

        final RequestLine requestLine = RequestLine.parse(requestReader.readLine());
        final HttpHeaders httpHeaders = readHeaders(requestReader);
        final String body = readBody(requestReader, httpHeaders.getContentLength());

        return new HttpRequest(requestLine, httpHeaders, body);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean matchPath(final String regex) {
        return Pattern.matches(regex, getPath());
    }

    public String getParameter(final String key) {
        return requestLine.getParameter(key);
    }

    public boolean matchMethod(final RequestMethod method) {
        return requestLine.matchMethod(method);
    }

    public String getBody() {
        return body;
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

    private static String readBody(final BufferedReader requestReader,
                                   final int contentLength) throws IOException {
        return IOUtils.readData(requestReader, contentLength);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", httpHeaders=" + httpHeaders +
                '}';
    }
}

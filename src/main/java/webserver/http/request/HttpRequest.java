package webserver.http.request;

import utils.IOUtils;
import utils.StringUtils;
import webserver.http.HeaderName;
import webserver.http.HttpMethod;
import webserver.http.cookie.Cookies;
import webserver.http.header.Headers;
import webserver.http.header.HttpHeaders;
import webserver.http.session.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.System.lineSeparator;
import static webserver.http.HttpMethod.POST;

public class HttpRequest implements Request {

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestQuery requestQuery;

    private Session session;

    private HttpRequest(final RequestLine requestLine,
                        final Headers headers,
                        final RequestQuery requestQuery) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestQuery = requestQuery;
    }

    public static HttpRequest of(final InputStream in) throws IOException {
        final BufferedReader requestReader = new BufferedReader(new InputStreamReader(in));

        final RequestLine requestLine = RequestLine.parse(requestReader.readLine());
        final Headers headers = readHeaders(requestReader);
        final RequestQuery requestQuery = requestLine.getParameters();

        if (requestLine.matchMethod(POST)) {
            final String body = readBody(requestReader, headers.getContentLength());
            requestQuery.join(RequestQuery.of(body));
        }

        return new HttpRequest(requestLine, headers, requestQuery);
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public boolean matchPath(final String regex) {
        return Pattern.matches(regex, getPath());
    }

    @Override
    public String getParameter(final String key) {
        return requestQuery.getString(key);
    }

    @Override
    public boolean matchMethod(final HttpMethod method) {
        return requestLine.matchMethod(method);
    }

    @Override
    public Optional<String> getHeader(final String key) {
        return Optional.ofNullable(headers.getString(key));
    }

    @Override
    public Optional<String> getHeader(final HeaderName key) {
        return getHeader(key.toString());
    }

    @Override
    public Cookies getCookies() {
        return headers.getCookies();
    }

    @Override
    public Session getSession() {
        return session;
    }

    public void setSession(final Session session) {
        this.session = session;
    }

    private static Headers readHeaders(final BufferedReader requestReader) throws IOException {
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
                ", headers=" + headers +
                ", requestQuery=" + requestQuery +
                ", session=" + session +
                '}';
    }
}

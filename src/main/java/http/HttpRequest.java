package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

import static com.github.jknack.handlebars.internal.lang3.BooleanUtils.negate;

public class HttpRequest {
    private RequestLine requestLine;
    private Headers headers = new Headers();
    private QueryStrings queryStrings;

    public static HttpRequest of(BufferedReader br) throws IOException {
        HttpRequest httpRequest = new HttpRequest();

        httpRequest.addRequestLine(br.readLine());
        initializeHttpHeaders(br, httpRequest);

        if (httpRequest.isPostRequest()) {
            initializeRequestBody(br, httpRequest);
        }

        return httpRequest;
    }

    private static void initializeHttpHeaders(BufferedReader br, HttpRequest httpRequest) throws IOException {
        String requestHeaderLine = br.readLine();

        while (negate("".equals(requestHeaderLine))) {
            httpRequest.addHeader(requestHeaderLine);
            requestHeaderLine = br.readLine();
        }
    }

    private static void initializeRequestBody(BufferedReader br, HttpRequest httpRequest) throws IOException {
        Optional<String> maybeValue = httpRequest.getHeaderValue(HeaderName.CONTENT_LENGTH);

        if(maybeValue.isPresent()) {
            String contentLength = maybeValue.get();
            httpRequest.addRequestBody(IOUtils.readData(br, Integer.parseInt(contentLength)));
        }
    }

    public void addRequestLine(String requestLineString) {
        this.requestLine = RequestLineParser.parse(requestLineString);
    }

    public void addHeader(String requestHeaderLine) {
        this.headers.add(requestHeaderLine);
    }

    public void addRequestBody(String requestBody) {
        this.queryStrings = new QueryStrings(requestBody);
    }

    public boolean matchPath(String requestPath) {
        return requestLine.matchPath(requestPath);
    }

    public boolean pathEndsWith(String extension) {
        return requestLine.pathEndsWith(extension);
    }

    public boolean isGetRequest() {
        return requestLine.isGetRequest();
    }

    public boolean isPostRequest() {
        return requestLine.isPostRequest();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public Optional<String> getHeaderValue(HeaderName headerType) {
        Optional<Header> maybeHeader = headers.getHeader(headerType);
        return maybeHeader.map(Header::getValue);

    }

    public QueryStrings getQueryString() {
        return this.queryStrings;
    }

}

package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import utils.IOUtils;

public class HttpRequestParser {

    private HttpRequestParser() {
        throw new AssertionError();
    }

    public static HttpRequest parse(final BufferedReader bufferedReader) throws IOException {
        final RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        final RequestHeaders requestHeaders = parseHeaders(bufferedReader);
        final RequestBody requestBody = parseBody(bufferedReader, requestHeaders);

        return new HttpRequest(requestLine, requestHeaders, requestBody);
    }

    private static RequestHeaders parseHeaders(final BufferedReader bufferedReader) throws IOException {
        final RequestHeaders requestHeaders = new RequestHeaders();
        String header = bufferedReader.readLine();
        while (!header.isEmpty()) {
            requestHeaders.add(header);
            header = bufferedReader.readLine();
        }
        return requestHeaders;
    }

    private static RequestBody parseBody(final BufferedReader bufferedReader, final RequestHeaders requestHeaders) throws IOException {
        if (requestHeaders.hasRequestBody()) {
            final String body = IOUtils.readData(bufferedReader, requestHeaders.getContentLength());
            return new RequestBody(body);
        }
        return RequestBody.EMPTY_REQUEST_BODY;
    }
}

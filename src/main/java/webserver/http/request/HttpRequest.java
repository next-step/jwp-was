package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.request.body.HttpRequestBody;
import webserver.http.request.headers.HttpRequestHeader;
import webserver.http.request.start_line.HttpRequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpRequestLine httpRequestLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(final InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        this.httpRequestLine = readHttpRequestLine(bufferedReader);
        this.httpRequestHeader = readHttpRequestHeader(bufferedReader);

        if (httpRequestHeader.hasBody()) {
            this.httpRequestBody = readHttpRequestBody(bufferedReader, httpRequestHeader.getContentLength());
            return;
        }
        this.httpRequestBody = HttpRequestBody.emptyBody();
    }

    private HttpRequestLine readHttpRequestLine(final BufferedReader bufferedReader) throws IOException {
        String readLine = bufferedReader.readLine();

        return new HttpRequestLine(readLine);
    }

    private HttpRequestHeader readHttpRequestHeader(final BufferedReader bufferedReader) throws IOException {
        List<String> headerValues = new ArrayList<>();
        String header = bufferedReader.readLine();

        while (header != null && !header.equals("")) {
            headerValues.add(header);
            header = bufferedReader.readLine();
        }

        return new HttpRequestHeader(headerValues);
    }

    private HttpRequestBody readHttpRequestBody(final BufferedReader bufferedReader, final int contentLength) throws IOException {
        String bodyValue = IOUtils.readData(bufferedReader, contentLength);

        return new HttpRequestBody(bodyValue);
    }

    public String getPathValue() {
        return httpRequestLine.getPath().getPath();
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }
}

package webserver.http.request;

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

    private final HttpRequestLine httpRequestLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(final InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        this.httpRequestLine = readHttpRequestLine(bufferedReader);
        this.httpRequestHeader = readHttpRequestHeader(bufferedReader);
        this.httpRequestBody = readHttpRequestBody(bufferedReader);
    }

    private HttpRequestLine readHttpRequestLine(final BufferedReader bufferedReader) {
        try {
            String readLine = bufferedReader.readLine();
            return new HttpRequestLine(readLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequestHeader readHttpRequestHeader(final BufferedReader bufferedReader) {
        try {
            List<String> headerValues = new ArrayList<>();
            String header = bufferedReader.readLine();

            while (header != null && !header.equals("")) {
                headerValues.add(header);
                header = bufferedReader.readLine();
            }

            return new HttpRequestHeader(headerValues);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequestBody readHttpRequestBody(final BufferedReader bufferedReader) throws IOException {
        String bodyValue = bufferedReader.readLine();

        while (bodyValue != null && !bodyValue.equals("")) {
            bodyValue.concat(bodyValue);
            bodyValue = bufferedReader.readLine();
        }

        return new HttpRequestBody(bodyValue);
    }

    public String getPathValue() {
        return httpRequestLine.getPath().getPath();
    }
}

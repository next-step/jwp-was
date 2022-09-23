package webserver.http.request;

import com.google.common.base.Charsets;
import utils.IOUtils;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestFactory {

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
        String line = bufferedReader.readLine();

        RequestLine requestLine = RequestLine.parse(line);
        HttpHeaders httpHeaders = initRequestHeaders(bufferedReader);

        if (httpHeaders.hasContent()) {
            HttpBody httpBody = initRequestBody(bufferedReader, httpHeaders.getContentLength());
            return new HttpRequest(requestLine, httpHeaders, httpBody);
        }
        return new HttpRequest(requestLine, httpHeaders, HttpBody.empty());

    }

    private static HttpHeaders initRequestHeaders(BufferedReader bufferedReader) throws IOException {
        HttpHeaders httpHeaders = HttpHeaders.init();
        String line = bufferedReader.readLine();

        while (!line.isEmpty()) {
            httpHeaders.addRequestHeader(line);
            line = bufferedReader.readLine();
        }

        return httpHeaders;
    }

    private static HttpBody initRequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        String request = IOUtils.readData(bufferedReader, contentLength);
        return HttpBody.from(request);
    }
}

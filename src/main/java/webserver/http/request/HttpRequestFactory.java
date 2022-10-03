package webserver.http.request;

import com.google.common.base.Charsets;
import utils.IOUtils;
import utils.StringUtils;
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
            RequestBody requestBody = initRequestBody(bufferedReader, httpHeaders.getContentLength());
            return new HttpRequest(requestLine, httpHeaders, requestBody);
        }
        return new HttpRequest(requestLine, httpHeaders, RequestBody.empty());

    }

    private static HttpHeaders initRequestHeaders(BufferedReader bufferedReader) throws IOException {
        HttpHeaders httpHeaders = HttpHeaders.init();
        String line = bufferedReader.readLine();

        while (!StringUtils.isNullAndBlank(line)) {
            httpHeaders.addRequestHeader(line);
            line = bufferedReader.readLine();
        }

        return httpHeaders;
    }

    private static RequestBody initRequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        String request = IOUtils.readData(bufferedReader, contentLength);
        return RequestBody.from(request);
    }
}

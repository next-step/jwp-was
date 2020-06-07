package http.request;

import lombok.Getter;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Getter
public class HttpRequest {
    private static final String CHAR_SET = "UTF-8";

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader) {
        this(requestLine, requestHeader, null);
    }

    public static HttpRequest getInstance(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())));

        RequestLine requestLine = RequestLine.getInstance(br.readLine());
        RequestHeader requestHeader = RequestHeader.getInstance(br);

        if (requestLine.getMethod() == HttpMethod.POST) {
            String body = IOUtils.readData(br, requestHeader.getContentLength());

            RequestBody requestBody = RequestBody.getInstance(URLDecoder.decode(body, CHAR_SET));
            return new HttpRequest(requestLine, requestHeader, requestBody);
        }

        return new HttpRequest(requestLine, requestHeader);
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getFilePath() {
        return this.requestLine.getFilePath();
    }

    public String getContentType() {
        return this.requestHeader.getContentType();
    }

    public Map<String, String> getBody() {
        return this.requestBody.getBodyMap();
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public boolean loggedIn() {
        return this.requestHeader.loggedIn();
    }
}

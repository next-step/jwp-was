package webserver.adapter.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.domain.http.HttpHeader;
import webserver.domain.http.HttpMethod;
import webserver.domain.http.RequestBody;
import webserver.domain.http.RequestLine;
import webserver.domain.http.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod httpMethod;
    private final Uri uri;
    private final HttpHeader httpHeader;
    private final RequestBody requestBody;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String readLine = br.readLine();
        logger.debug("{}", readLine);
        RequestLine requestLine = RequestLine.from(readLine);

        HttpHeader httpHeader = getHttpHeader(br);
        RequestBody httpBody = RequestBody.from(IOUtils.readData(br, httpHeader.getContentLength()));

        this.httpMethod = requestLine.getHttpMethod();
        this.uri = requestLine.getUri();
        this.httpHeader = httpHeader;
        this.requestBody = httpBody;
    }

    private HttpHeader getHttpHeader(BufferedReader br) throws IOException {
        String line = null;
        List<String> request = new ArrayList<>();

        while (!"".equals(line)) {
            line = br.readLine();

            if (Objects.isNull(line)) {
                break;
            }

            logger.debug("{}", line);
            request.add(line);
        }

        return HttpHeader.from(request);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Uri getUri() {
        return uri;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpMethod=" + httpMethod +
                ", uri=" + uri +
                ", httpHeader=" + httpHeader +
                ", requestBody=" + requestBody +
                '}';
    }
}

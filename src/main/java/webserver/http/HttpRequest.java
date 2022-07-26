package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private Headers headers;
    private RequestLine requestLine;
    private HttpBody httpBody;

    public HttpRequest(InputStream inputStream) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = br.readLine();
            if (line == null) {
                return;
            }
            this.requestLine = new RequestLine(line);
            this.headers = new Headers(br);
            if (requestLine.getMethod().isPost()) {
                String body = IOUtils.readData(br, headers.getBodySize());
                this.httpBody = new HttpBody(body);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }
}

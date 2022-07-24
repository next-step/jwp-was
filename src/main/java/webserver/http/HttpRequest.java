package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private Headers headers;
    private RequestLine requestLine;

    public HttpRequest(InputStream inputStream) {
        this.headers = new Headers();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = br.readLine();
            if (line == null) {
                return;
            }
            this.requestLine = new RequestLine(line);
            line = br.readLine();
            while (!line.equals("")) {
                headers.putHeader(line);
                line = br.readLine();
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
}

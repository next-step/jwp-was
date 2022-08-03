package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.constant.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpMethod method;
    private String path;
    private Map<String, String> header;
    private Map<String, String> parameter;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();
        logger.debug("requst line : {}", line);
        if (line == null) {
            return;
        }
        RequestLine requestLine = new RequestLine(line);
        this.path = requestLine.getUri().getPath();
        this.method = requestLine.getMethod();

        Map<String, String> headers = new HashMap<>();
        while (!line.equals("")) {
            line = br.readLine();
            logger.debug("header: {}", line);
            HeaderParser httpHeader = new HeaderParser(line);
            headers.put(httpHeader.getHeaderName(), httpHeader.getHeaderValue());
        }
        this.header = headers;

        if (method == HttpMethod.GET) {
            String queryString = requestLine.getUri().getQueryString();
            if (queryString != null){
                this.parameter = new QueryStringParser(queryString).getQueryParameters();
            }
        }
        if (method == HttpMethod.POST) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            QueryStringParser queryStringParser = new QueryStringParser(IOUtils.readData(br, contentLength));
            this.parameter = queryStringParser.getQueryParameters();
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String name) {
        System.out.println(header);
        return header.get(name);
    }

    public String getParameter(String name) {
        return parameter.get(name);
    }
}

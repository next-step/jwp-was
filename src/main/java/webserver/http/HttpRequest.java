package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.constant.HttpMethod;
import webserver.http.requestLine.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpMethod method;
    private String path;
    private HttpHeader header = new HttpHeader();
    private HttpParameter parameter = new HttpParameter();

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

        while (!line.equals("")) {
            line = br.readLine();
            logger.debug("header: {}", line);
            header.loadHeader(line);
        }

        String queryString = requestLine.getUri().getQueryString();
        if (queryString != null) {
            parameter.loadParameters(queryString);
        }
        if (method == HttpMethod.POST) {
            int contentLength = Integer.parseInt(header.getHeaderValue("Content-Length"));
            parameter.loadParameters(IOUtils.readData(br, contentLength));
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
        return header.getHeaderValue(name);
    }

    public String getParameter(String name) {
        return parameter.getParameters(name);
    }
}

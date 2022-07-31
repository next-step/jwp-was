package webserver.http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private static final String EMPTY_STRING = "";
    private RequestLine requestLine;
    private RequestHeader requestHeader = new RequestHeader();
    private RequestBody requestBody;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = br.readLine();
        if (line == null) {
            return;
        }

        requestLine = new RequestLine(line);
        requestBody = new RequestBody(requestLine.getQueryString());

        line = br.readLine();
        while (!EMPTY_STRING.equals(line)) {
            requestHeader.addHeader(line);
            line = br.readLine();
        }

        requestBody.parse(IOUtils.readData(br, requestHeader.getContentLength()));
    }

    public boolean isSameMethod(HttpMethod method) {
        return method.equals(requestLine.getMethod());
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getParameter(String parameter) {
        return requestBody.getParameter(parameter);
    }

    public String getHeader(String key) {
        return requestHeader.getHeader(key);
    }

    public Cookies getCookie() {
        return requestHeader.getCookie();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }
}

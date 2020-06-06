package http;

import utils.IOUtils;
import utils.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {
    private static final String PARAMETER_DELIMITER = "&";

    private RequestLine requestLine;
    private RequestHeader requestHeader = RequestHeader.init();
    private String bodyOrigin;

    private HttpRequest(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        initRequestHeader(bufferedReader);
        initHeaders(bufferedReader);
        initBody(bufferedReader);
    }

    public static HttpRequest readRawRequest(final InputStream inputStream) throws IOException {
        return new HttpRequest(inputStream);
    }

    private void initRequestHeader(final BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();
        String decodedReadStream = URLDecoder.decode(requestLine, "UTF-8");

        this.requestLine = RequestLine.init(decodedReadStream);
    }

    private void initHeaders(final BufferedReader bufferedReader) throws IOException {
        String headerLine;

        while (!StringUtil.isEmpty(headerLine = bufferedReader.readLine())) {
            requestHeader.addHeader(headerLine);
        }
    }

    private void initBody(final BufferedReader bufferedReader) throws IOException {
        if (getHeader("Content-Length") == null) {
            bodyOrigin = "";
            return;
        }

        int contentLength = Integer.parseInt(getHeader("Content-Length"));
        bodyOrigin = URLDecoder.decode(IOUtils.readData(bufferedReader, contentLength), "UTF-8");

        StringTokenizer tokens = new StringTokenizer(bodyOrigin, PARAMETER_DELIMITER);
        while (tokens.hasMoreTokens()) {
            requestHeader.addHeader(tokens.nextToken());
        }
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getExtension() {
        return requestLine.getExtension();
    }

    public String getParameter(final String parameter) {
        return requestLine.getParameter(parameter);
    }

    public String getProtocol() {
        return requestLine.getProtocol();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public String getHeader(final String headerName) {
        return requestHeader.getHeader(headerName);
    }

    public String getCookie(final String cookieName) {
        return requestHeader.getCookie(cookieName);
    }

    public Map<String, String> getParameters() {
        return requestHeader.getParameters();
    }

    @Override
    public String toString() {
        return requestLine.toString() + "\n" +
                requestHeader.toString() + "\n\n" +
                bodyOrigin;
    }
}

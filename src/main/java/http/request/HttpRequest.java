package http.request;

import session.HttpSession;
import utils.IOUtils;
import utils.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private Parameters parameters;
    private HttpSession httpSession;
    private String bodyOrigin;

    private HttpRequest(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        initRequestLine(bufferedReader);
        initHeaders(bufferedReader);
        initBody(bufferedReader);
    }

    public static HttpRequest readRawRequest(final InputStream inputStream) throws IOException {
        return new HttpRequest(inputStream);
    }

    private void initRequestLine(final BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();
        String decodedReadStream = URLDecoder.decode(requestLine, "UTF-8");

        this.requestLine = RequestLine.parse(decodedReadStream);
        this.parameters = Parameters.parse(this.requestLine);
    }

    private void initHeaders(final BufferedReader bufferedReader) throws IOException {
        requestHeader = new RequestHeader();
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
        parameters.addParameters(bodyOrigin);
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
        return parameters.getParameter(parameter);
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
        return parameters.getParameters();
    }

    @Override
    public String toString() {
        return requestLine.toString() + "\n" +
                requestHeader.toString() + "\n\n" +
                bodyOrigin;
    }

    public void setSession(final HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public HttpSession getSession() {
        return httpSession;
    }
}

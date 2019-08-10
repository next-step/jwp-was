package webserver.http.request;

import utils.IOUtils;
import webserver.http.request.exception.HttpMethodNotSupportedException;
import webserver.http.request.support.RequestParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-05
 */
public class HttpRequest {

    private static final String END_OF_LINE = "";
    private static final String REQUEST_HEADER_DELIMITER = ": ";
    private static final String COOKIE_FIELD_NAME = "Cookie";
    private static final String REQUEST_LINE_SEPARATOR = " ";

    private HttpMethod httpMethod;
    private String path;
    private ParameterMap parameters = new ParameterMap();
    private String httpVersion;
    private Map<String, String> headers;

    public HttpRequest(InputStream in) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String line = bufferedReader.readLine();
        parseRequestLine(line);

        headers = new HashMap<>();
        while (!(line = bufferedReader.readLine()).equals(END_OF_LINE)) {
            int indexOfHeaderKey = line.indexOf(REQUEST_HEADER_DELIMITER);
            headers.put(line.substring(0, indexOfHeaderKey), line.substring(indexOfHeaderKey + REQUEST_HEADER_DELIMITER.length()));
        }

        String contentLength = headers.get("Content-Length");
        if (contentLength != null) {
            parameters.putAll(RequestParser.parseQuery(IOUtils.readData(bufferedReader, Integer.parseInt(contentLength))));
        }
    }

    private void parseRequestLine(String requestLine) {
        String[] parsedLines = requestLine.split(REQUEST_LINE_SEPARATOR);
        if (!HttpMethod.contains(parsedLines[0])) {
            throw new HttpMethodNotSupportedException();
        }

        httpMethod = HttpMethod.valueOf(parsedLines[0]);
        path = RequestParser.parsePathFromRequestURI(parsedLines[1]);
        parameters.putAll(RequestParser.parseQueryFromRequestURI(parsedLines[1]));
        httpVersion = parsedLines[2];

    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getCookie() {
        return headers.get(COOKIE_FIELD_NAME);
    }

    public String getPath() {
        return this.path;
    }

    public String getParameter(String parameterName) {
        return parameters.get(parameterName);
    }

    public ParameterMap getParameters() {
        return parameters;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}

package webserver.http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author : yusik
 * @date : 2019-08-05
 */
public class HttpRequest {

    private static final String END_OF_LINE = "";
    private static final String REQUEST_HEADER_DELIMITER = ": ";
    private RequestLine requestLine;
    private Map<String, String> headers;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {

        String line = bufferedReader.readLine();
        this.requestLine = RequestLine.parse(line);

        headers = new HashMap<>();
        while (!(line = bufferedReader.readLine()).equals(END_OF_LINE)) {
            int indexOfHeaderKey = line.indexOf(REQUEST_HEADER_DELIMITER);
            headers.put(line.substring(0, indexOfHeaderKey), line.substring(indexOfHeaderKey + REQUEST_HEADER_DELIMITER.length()));
        }

        String contentLength = headers.get("Content-Length");
        if (contentLength != null) {
            setQueryString(QueryString.parse(IOUtils.readData(bufferedReader, Integer.parseInt(contentLength))));
        }
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getPath() {
        return this.getRequestLine().getRequestURI().getPath();
    }

    public void setQueryString(QueryString queryString) {
        RequestURI requestURI = this.getRequestLine().getRequestURI();
        requestURI.setQueryString(queryString);
    }

    public ParameterMap getParameters() {
        return Optional.ofNullable(this.getRequestLine().getRequestURI().getQueryString())
                .map(QueryString::getParameters)
                .orElse(null);
    }

    public HttpMethod getHttpMethod() {
        return getRequestLine().getMethod();
    }
}

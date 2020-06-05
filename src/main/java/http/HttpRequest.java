package http;

import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpRequest {

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private Parameters parameters;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        this.requestLine = RequestLine.from(bufferedReader.readLine());

        setHttpHeaders(bufferedReader);
        setParameters(bufferedReader);
    }

    private void setHttpHeaders(BufferedReader bufferedReader) throws IOException {
        String headerString = getHeaderString(bufferedReader);
        HttpHeaders httpHeaders = HttpHeaders.emptyHeaders();

        if(!StringUtils.isEmpty(headerString)) {
            httpHeaders = HttpHeaders.from(headerString);
        }

        this.httpHeaders = httpHeaders;
    }

    private void setParameters(BufferedReader bufferedReader) throws IOException {
        HttpMethod httpMethod = requestLine.getMethod();
        String contentLength = this.httpHeaders.getHeader(HttpHeaders.CONTENT_LENGTH);

        this.parameters = requestLine.getParameters();

        if(HttpMethod.GET.equals(httpMethod) || contentLength == null) {
            return;
        }

        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength.trim()));

        if(StringUtils.isEmpty(body)) {
            return;
        }

        parameters = Parameters.from(body);
        parameters.addAll(requestLine.getParameterMap());
    }

    private String getHeaderString(BufferedReader bufferedReader) throws IOException {
        List<String> headerStrings = new ArrayList<>();

        String header;
        while((header = bufferedReader.readLine()) != null && !StringUtils.isEmpty(header)) {
            headerStrings.add(header);
        }

        return String.join("\n", headerStrings.toArray(new String[]{}));
    }

    public static HttpRequest from(BufferedReader bufferedReader) throws IOException {
        return new HttpRequest(bufferedReader);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public String getParameter(String key) {
        return parameters.getParameter(key);
    }

    public MultiValueMap<String, String> getParameterMap() {
        return parameters;
    }
}

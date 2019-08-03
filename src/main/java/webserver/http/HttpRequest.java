package webserver.http;

import enums.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import utils.IOUtils;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class HttpRequest {

    private final RequestLine requestLine;
    private final String requestUri;
    private final HttpHeaders httpHeaders;
    private final String body;
    private final MultiValueMap<String, String> params;


    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, String body){
        this.requestLine = requestLine;
        this.requestUri = parseRequestUri(requestLine.getPath());
        this.httpHeaders = httpHeaders;
        this.body = body;
        this.params = new LinkedMultiValueMap<>(QueryParams.parseByPath(requestLine.getPath()).getParameters());
    }

    public static String parseRequestUri(String path) {
        return path.replaceAll("http(s)?://.*?(?=/)", "")
                .replaceAll("\\?.*$", "");
    }

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = new HttpHeaders();
        String headerLine;
        while (!StringUtils.isEmpty(headerLine = bufferedReader.readLine())) {
            httpHeaders.addHeaderLine(headerLine);
        }


        String body = null;
        int contentLength = (int)httpHeaders.getContentLength();
        if(contentLength != -1) {
            body = IOUtils.readData(bufferedReader, contentLength);
        }

        return new HttpRequest(requestLine, httpHeaders, body);
    }

    public HttpMethod getMethod() {return this.requestLine.getMethod();}

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getParameter(String name) {
        return Optional.ofNullable(getParameterValues(name))
                .filter(values -> values.length > 0)
                .map(values -> values[0])
                .orElse(null);
    }

    public String[] getParameterValues(String name) {
        return Optional.ofNullable(this.params.get(name))
                .map(values -> values.toArray(new String[values.size()]))
                .orElse(null);
    }

    public String getHeader(String name) {
        return this.httpHeaders.getHeaderValueFirst(name);
    }

    public String getBody(){
        return this.body;
    }

    public String getRequestURI() {
        return this.requestUri;
    }


}

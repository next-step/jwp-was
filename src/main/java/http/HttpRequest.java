package http;

import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import utils.CookieUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

public class HttpRequest {

    public static final String  CHAR_SET = "UTF-8";

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private Parameters parameters;
    private Set<Cookie> cookies;

    private HttpRequest(BufferedReader bufferedReader) throws IOException {
        this.requestLine = RequestLine.from(bufferedReader.readLine());

        setHttpHeaders(bufferedReader);
        setParameters(bufferedReader);
        setCookies();
    }

    private void setCookies() {
        String cookiesString = this.httpHeaders.getHeader(HttpHeaders.COOKIE);

        if(cookiesString == null || StringUtils.isEmpty(cookiesString)) {
            this.cookies = new LinkedHashSet<>();
            return;
        }

        this.cookies = CookieUtils.stringToCookies(cookiesString);
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

        String body = URLDecoder.decode(IOUtils.readData(bufferedReader, Integer.parseInt(contentLength.trim())), HttpRequest.CHAR_SET);

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

    public String getHeader(String name) {
        return httpHeaders.getHeader(name);
    }

    public String getParameter(String name) {
        return parameters.getParameter(name);
    }

    public MultiValueMap<String, String> getParameterMap() {
        return parameters;
    }

    public Set<Cookie> getCookies() {
        return cookies;
    }

    public Cookie getCookie(String name) {
        return cookies.stream()
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst().orElse(null);
    }
}

package webserver.http;

import utils.IOUtils;
import webserver.http.request.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final Cookie cookie;
    private final Parameters parameters;
    private final Map<String, Object> attributes = new HashMap<>();

    private HttpRequest(RequestLine requestLine, HttpHeaders headers, Cookie cookie, Parameters parameters) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.cookie = cookie;
        this.parameters = parameters;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String requestLineString = br.readLine();
        List<String> headerLineStrings = new ArrayList<>();
        String nextLine = br.readLine();
        while (!Objects.isNull(nextLine) && !"".equals(nextLine)) {
            headerLineStrings.add(nextLine);
            nextLine = br.readLine();
        }
        String headerLineString = String.join("\n", headerLineStrings);

        RequestLine requestLine = RequestLine.from(requestLineString);
        HttpMethod method = requestLine.getMethod();
        Path path = requestLine.getPath();
        HttpHeaders headers = HttpHeaders.from(headerLineString);
        Cookie cookie = Cookie.from(headers.get(Cookie.REQUEST_COOKIE_HEADER));

        String requestBody = "";
        if (HttpMethod.POST.equals(method)) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            requestBody = IOUtils.readData(br, contentLength);
        }

        Parameters parameters = HttpMethod.GET.equals(method) ? path.getQueries() : Parameters.from(requestBody);

        return new HttpRequest(requestLine, headers, cookie, parameters);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        Path path = requestLine.getPath();
        return Path.PATH_DELIMITER + path.toString();
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Map<String, Object> getAttributes() {
        return new HashMap<>(attributes);
    }
}

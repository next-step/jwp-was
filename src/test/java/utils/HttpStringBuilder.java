package utils;

import http.HttpHeaders;
import http.HttpMethod;
import http.HttpStatus;
import http.Parameters;

import java.util.List;
import java.util.stream.Collectors;

public class HttpStringBuilder {

    private String path = "/";
    private HttpMethod httpMethod = HttpMethod.GET;
    private HttpStatus httpStatus = HttpStatus.OK;
    private HttpHeaders httpHeaders = HttpHeaders.emptyHeaders();
    private Parameters parameters = Parameters.emptyParameters();
    private String body = "";

    private HttpStringBuilder() { }

    public static HttpStringBuilder builder() {
        return new HttpStringBuilder();
    }

    public HttpStringBuilder path(String path) {
        this.path = path;
        return this;
    }

    public HttpStringBuilder addParameter(String name, String value) {
        this.parameters.add(name, value);
        return this;
    }

    public HttpStringBuilder httpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpStringBuilder httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public HttpStringBuilder addHeader(String name, String value) {
        this.httpHeaders.add(name, value);
        return this;
    }

    public HttpStringBuilder body(String body) {
        this.body = body;
        return this;
    }

    public String buildRequest() {
        String queryString = parameterToQueryString();
        String resultString = httpMethod.name() + " " + path + queryString + " HTTP/1.1\r\n";

        resultString += getHeaderString();
        resultString += getBodyString();
        return resultString;
    }

    public String buildResponse() {
        String resultString = "HTTP/1.1 " + httpStatus.getValue() + " " + httpStatus.getMessage() + "\r\n";

        resultString += getHeaderString();
        resultString += getBodyString();

        return resultString;
    }

    public String buildHeaders() {
        return getHeaderString();
    }

    private String getHeaderString() {
        String resultString = "";
        for (String name : httpHeaders.keySet()) {
            List<String> headerValues = httpHeaders.get(name);

            if(headerValues == null) {
                continue;
            }

            for (String headerValue : headerValues) {
                resultString += name + ": " + headerValue + "\r\n";
            }
        }

        resultString += System.lineSeparator();
        return resultString;
    }

    private String getBodyString() {
        String resultString = "";
        if(body != null && !"".equals(body)) {
            resultString += body;
        }
        return resultString;
    }
    private String parameterToQueryString() {
        if(parameters.size() == 0) {
            return "";
        }

        String result = parameters.keySet().stream()
                .map(key -> key + "=" + String.join(",", parameters.get(key)))
                .collect(Collectors.joining("&"));
        return "?" + result;
    }
}

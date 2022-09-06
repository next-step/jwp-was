package webserver.http;

import exception.ValidateException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpBody {

    private final QueryString queryString;

    private HttpBody(String request) {
        validate(request);
        this.queryString = new QueryString(request);
    }

    public static HttpBody from(String request) {
        return new HttpBody(request);
    }

    public static HttpBody of(BufferedReader bufferedReader, int contentLength) throws IOException {
        String request = IOUtils.readData(bufferedReader, contentLength);
        return new HttpBody(request);
    }

    private void validate(String request) {
        if (request == null || request.length() == 0) {
            throw new ValidateException();
        }
    }

    public QueryString getQueryString() {
        return queryString;
    }

    public Map<String, String> getQueryStringMap() {
        return queryString.getParameterMap();
    }

    @Override
    public String toString() {
        return "HttpBody{" +
                "queryString=" + queryString +
                '}';
    }
}

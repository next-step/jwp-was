package webserver.request.domain.request;

import com.google.common.base.Charsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final String DELIMITER1 = " ";
    private static final String DELIMITER2 = "\\?";

    private Method method;
    private String path;
    private QueryString queryString;
    private RequestHeader requestHeader = new RequestHeader();
    private RequestBody requestBody;

    public HttpRequest(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
        String line = br.readLine();

        parseRequestLine(line);

        parseHeaders(br, line);

        if(method.equals(Method.POST)) {
            line = br.readLine();
            requestBody = new RequestBody(line);
        }
    }

    private void parseHeaders(BufferedReader br, String line) throws IOException {
        while (!line.equals("")) {
            line = br.readLine();
            requestHeader.addHeaderProperty(line);
        }
    }

    private void parseRequestLine(String line) {
        String[] arrays = line.split(DELIMITER1);
        method = Method.valueOf(arrays[0]);

        if (method.toString().equals("GET")) {
            parseUrl(arrays[1]);
            return;
        }

        path = arrays[1];
    }

    private void parseUrl(String url) {
        String[] arrays = url.split(DELIMITER2);

        path = arrays[0];

        if(arrays.length == 2) {
            queryString = new QueryString(arrays[1]);
        }
    }

    public String getMethod() {
        return method.name();
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String header) {
        return requestHeader.getHeader(header);
    }

    public String getParameter(String parameter) {
        if(method.equals(Method.GET))
            return queryString.getParameter(parameter);
        return requestBody.getParameter(parameter);
    }
}

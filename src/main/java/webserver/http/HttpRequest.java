package webserver.http;

import utils.IOUtils;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private final RequestLine requestLine;
    private final QueryParams queryParams;
    private final HttpHeaders httpHeaders;
    private final String body;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, String body){
        this.requestLine = requestLine;
        this.queryParams = QueryParams.parseByPath(requestLine.getPath());
        this.httpHeaders = httpHeaders;
        this.body = body;
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


    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getParameter(String name) {
        return this.queryParams.getParameter(name);
    }

    public String[] getParameterValues(String name) {
        return this.queryParams.getParameterValues(name);
    }

    public String getHeader(String name) {
        return this.httpHeaders.getHeaderValueFirst(name);
    }

    public String getBody(){
        return this.body;
    }


}

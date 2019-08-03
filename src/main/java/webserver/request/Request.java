package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private RequestLine requestLine;
    private RequestHeader requestHeader;

    public Request(RequestLine requestLine, RequestHeader requestHeader) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
    }

    public static Request parse(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> requestHeaders = new ArrayList<>();
        RequestLine requestLine = RequestLine.parse(reader.readLine());

        String header;
        while ((header = reader.readLine()) != null) {
            requestHeaders.add(header);
        }

        return new Request(requestLine, RequestHeader.parse(requestHeaders));
    }

    public String getPath() {
        return this.requestLine.getPath();
    }
}

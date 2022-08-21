package webserver.http;

import com.google.common.base.Charsets;
import model.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private RequestLine requestLine;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
        String line = bufferedReader.readLine();

        if (line == null) {
            return;
        }

        requestLine = RequestLine.parse(line);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                '}';
    }
}

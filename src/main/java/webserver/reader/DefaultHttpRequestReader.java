package webserver.reader;

import http.HttpRequest;

import java.io.IOException;
import java.io.InputStream;

public class DefaultHttpRequestReader implements HttpRequestReader {

    @Override
    public HttpRequest read(InputStream inputStream) throws IOException {
        return HttpRequest.readRawRequest(inputStream);
    }
}

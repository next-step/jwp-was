package webserver.reader;

import http.HttpRequest;

import java.io.IOException;
import java.io.InputStream;

public interface HttpRequestReader {
    HttpRequest read(InputStream inputStream) throws IOException;
}

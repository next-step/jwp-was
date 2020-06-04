package webserver.reader;

import http.HttpRequest;
import http.HttpRequestParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DefaultHttpRequestReader implements HttpRequestReader {
    private static final DefaultRequestReader DEFAULT_REQUEST_READER = new DefaultRequestReader();

    @Override
    public HttpRequest read(InputStream inputStream) throws IOException {
        String readStream = DEFAULT_REQUEST_READER.readStream(inputStream);
        String decodedReadStream = URLDecoder.decode(readStream, "UTF-8");

        return HttpRequestParser.parse(decodedReadStream);
    }
}

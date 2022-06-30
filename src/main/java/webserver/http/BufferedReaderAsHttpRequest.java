package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;

public class BufferedReaderAsHttpRequest implements HttpRequestPipe {
    private static final Logger log = LoggerFactory.getLogger(BufferedReaderAsHttpRequest.class);

    private final BufferedReader bufferedReader;

    public BufferedReaderAsHttpRequest(InputStream inputStream) {
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public BufferedReaderAsHttpRequest(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public HttpRequest request() {
        try {
            RequestLine requestLine = new RequestLine(createRequestLine(bufferedReader));
            HttpHeaders headers = processHeaders(bufferedReader);
            String body = IOUtils.readData(bufferedReader, headers.getContentLength());
            return new HttpRequest(requestLine, headers, body);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static String createRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new IllegalStateException();
        }
        return line;
    }

    private static HttpHeaders processHeaders(BufferedReader br) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String line;
        while (!(line = br.readLine()).equals("")) {
            headers.add(line);
        }
        return headers;
    }
}

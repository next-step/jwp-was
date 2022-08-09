package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpRequestParser {
    public static final String VALIDATION_MESSAGE = "잘못된 요청입니다.";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestParser.class);
    private static final Pattern HEADER_DELIMITER = Pattern.compile(": ");
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("&?([^=&]+)=([^=&]+)");
    private static final String CONTENT_LENGTH_KEY = "Content-Length";

    private HttpRequestParser() {
        throw new AssertionError();
    }

    public static HttpRequest parse(InputStream in) {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        final RequestLine requestLine = makeRequestLine(br);
        final Headers headers = makeHeaders(br);
        final Attributes attributes = makeAttributes(br, headers);
        return new HttpRequest(requestLine, headers, attributes);
    }

    private static RequestLine makeRequestLine(BufferedReader br) {
        final String line = readLine(br);
        return new RequestLine(line);
    }

    private static Headers makeHeaders(BufferedReader br) {
        Map<String, Object> headers = new HashMap<>();
        String line;
        while (!"".equals(line = readLine(br))) {
            final String[] splitLine = HEADER_DELIMITER.split(line);
            headers.put(splitLine[0], splitLine[1]);
        }
        return new Headers(headers);
    }

    private static String readLine(BufferedReader br) {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE, e);
        }
        return line == null ? "" : line;
    }

    private static Attributes makeAttributes(BufferedReader br, Headers headers) {
        if (!headers.hasHeader(CONTENT_LENGTH_KEY)) {
            return new Attributes();
        }

        try {
            final int contentLength = headers.getHeader(CONTENT_LENGTH_KEY, Integer.class);
            final String payload = IOUtils.readData(br, contentLength);
            return new Attributes(HttpUtils.parseParameters(payload, ATTRIBUTE_PATTERN));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return new Attributes();
        }
    }
}

package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.base.Strings;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestStream {
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String HEADER_SEPARATOR = ": ";

    private BufferedReader bufferedReader;
    private List<String> requestLines;
    private String body;

    public RequestStream(InputStream inputStream) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        requestLines = parseRequestLines(bufferedReader);
    }

    public String requestLine() {
        return this.requestLines.get(0);
    }

    public List<String> header() {
        return this.requestLines.subList(1, requestLines.size() - 1);
    }

    public String body() throws IOException {
        if (Strings.isNullOrEmpty(body)) {
            body = IOUtils.readData(bufferedReader, getContentLength());
        }

        return body;
    }

    private static List<String> parseRequestLines(BufferedReader bufferedReader) throws IOException {
        String line;
        List<String> headerLines = new ArrayList<>();
        while (!StringUtils.EMPTY.equals(line = bufferedReader.readLine())) {
            if (Objects.isNull(line)) break;
            headerLines.add(line);
        }
        return headerLines;
    }

    private int getContentLength() {
        return header().stream()
                .filter(h -> h.contains(CONTENT_LENGTH_HEADER))
                .map(h -> Integer.parseInt(h.split(HEADER_SEPARATOR)[1]))
                .findAny()
                .orElse(0);
    }

}

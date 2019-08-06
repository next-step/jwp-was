package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestStream {
    private BufferedReader bufferedReader;
    private boolean readRequestLine;
    private boolean readHeader;
    private boolean readBody;

    public RequestStream(InputStream inputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.readRequestLine = false;
        this.readHeader = false;
        this.readBody = false;
    }

    public String requestLine() throws IOException {
        if (this.readRequestLine)
            throw new RuntimeException("requestLine을 이미 읽었습니다.");

        this.readRequestLine = true;
        return this.bufferedReader.readLine();
    }

    public List<String> header() throws IOException {
        if (this.readHeader)
            throw new RuntimeException("header를 이미 읽었습니다.");

        this.readHeader = true;
        return parseHeaderLines(this.bufferedReader);
    }

    public String body(int length) throws IOException {
        if (this.readBody)
            throw new RuntimeException("body를 이미 읽었습니다.");

        this.readBody = true;
        return IOUtils.readData(bufferedReader, length);
    }

    private static List<String> parseHeaderLines(BufferedReader bufferedReader) throws IOException {
        String line;
        List<String> headerLines = new ArrayList<>();
        while (!StringUtils.EMPTY.equals(line = bufferedReader.readLine())) {
            if (Objects.isNull(line)) break;
            headerLines.add(line);
        }
        return headerLines;
    }
}

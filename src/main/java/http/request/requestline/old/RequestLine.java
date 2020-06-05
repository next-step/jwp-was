package http.request.requestline.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestLine {
    private static final String BLANK_ONE = " ";

    private HttpMethod httpMethod;
    private Path path;
    private ProtocolAndVersion protocolAndVersion;

    public RequestLine(String requestLine) {
        String[] strRequestLine = requestLine.split(BLANK_ONE);
        this.httpMethod = HttpMethod.valueOf(strRequestLine[0]);
        this.path = new Path(strRequestLine[1]);
        this.protocolAndVersion = new ProtocolAndVersion(strRequestLine[2]);
    }

    public static RequestLine of(BufferedReader br) throws IOException {
        return new RequestLine(br.readLine());
    }

    public static RequestLine parse(String strRequestLine) {
        return new RequestLine(strRequestLine);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getStringPath() {
        return path.getStringPath();
    }

    public Map<String, String> getQueryStrings() {
        return path.getQueryStrings();
    }

    public ProtocolAndVersion getProtocolAndVersion() {
        return protocolAndVersion;
    }
}
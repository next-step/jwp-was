package response;

import constant.HttpContentType;
import constant.HttpHeader;
import constant.HttpStatusCode;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import utils.FileIoUtils;

public class HttpResponse {

    private final HttpStatusCode code;
    private final ResponseHeader header;
    private final byte[] body;

    public HttpResponse(HttpStatusCode code, ResponseHeader header, byte[] body) {
        this.code = code;
        this.header = header;
        this.body = body;
    }

    public static HttpResponse of(HttpStatusCode code, ResponseHeader header, byte[] body) {
        return new HttpResponse(code, header, body);
    }

    public static HttpResponse forward(String view) throws IOException, URISyntaxException {
        HttpContentType contentType = HttpContentType.of(view);

        HttpResponse response = new HttpResponse(
            HttpStatusCode.OK,
            ResponseHeader.empty(),
            FileIoUtils.loadFileFromClasspath(contentType.getResourcePath() + view)
        );
        response.addHeader(HttpHeader.CONTENT_LENGTH.getValue(), String.valueOf(response.getBody().length));
        response.addHeader(HttpHeader.CONTENT_TYPE.getValue(), contentType.getValue());

        return response;
    }

    public static HttpResponse sendRedirect(String view) throws IOException, URISyntaxException {
        HttpContentType contentType = HttpContentType.of(view);
        Map<String, String> header = new HashMap<>();
        header.put(HttpHeader.LOCATION.getValue(), view);

        HttpResponse response = new HttpResponse(
            HttpStatusCode.FOUND,
            ResponseHeader.from(header),
            FileIoUtils.loadFileFromClasspath(contentType.getResourcePath() + view)
        );
        response.addHeader(HttpHeader.CONTENT_LENGTH.getValue(), String.valueOf(response.getBody().length));
        response.addHeader(HttpHeader.CONTENT_TYPE.getValue(), contentType.getValue());

        return response;
    }

    public static HttpResponse sendTemplate(String templatePage) throws IOException, URISyntaxException {
        HttpResponse response = new HttpResponse(
            HttpStatusCode.OK,
            ResponseHeader.empty(),
            templatePage.getBytes(StandardCharsets.UTF_8)
        );
        response.addHeader(HttpHeader.CONTENT_LENGTH.getValue(), String.valueOf(response.getBody().length));
        response.addHeader(HttpHeader.CONTENT_TYPE.getValue(), HttpContentType.TEXT_HTML.getType());

        return response;
    }

    public void addHeader(String key, String value) {
        header.add(key, value);
    }

    public Set<Entry<String, String>> getHeaderEntiry() {
        return header.entries();
    }

    public HttpStatusCode getCode() {
        return code;
    }

    public String getResponseCode() {
        return code.getCode() + " " + code.getMessage();
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }
}

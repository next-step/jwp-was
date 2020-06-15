package http;

import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpResponse {
    private final StatusLine statusLine;
    private final ResponseHeaders headers;
    private final ResponseBody body;

    private HttpResponse(StatusLine statusLine, ResponseHeaders headers, ResponseBody body) {
        // 방어로직
        if (statusLine == null) {
            statusLine = StatusLine.makeEmptyStatusLine();
        }

        if (headers == null) {
            headers = ResponseHeaders.makeEmptyResponseHeaders();
        }

        if (body == null) {
            body = ResponseBody.makeEmptyResponseBody();
        }

        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    @Nonnull
    // TODO 넘겨 줄 때 content-type이 넘어가게. content-length도 함께?
    public static HttpResponse from(String filePath, HttpStatus httpStatus) throws IOException, URISyntaxException {
        StatusLine statusLine = makeStatusLine(httpStatus);
        ResponseHeaders responseHeaders = makeContentHeaders(filePath);
        ResponseBody responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(filePath));

        return new HttpResponse(statusLine, responseHeaders, responseBody);
    }

    @Nonnull
    public static HttpResponse from(byte[] responseBodyByteArray, HttpStatus httpStatus) throws IOException, URISyntaxException {
        StatusLine statusLine = makeStatusLine(httpStatus);
        // TODO 여기도 contentType 과 length 는 역할을 따로 빼야 할 것 같음.
        ResponseHeaders responseHeaders = makeContentHeaders("text/html;charset=utf-8", responseBodyByteArray.length);
        ResponseBody responseBody = new ResponseBody(responseBodyByteArray);

        return new HttpResponse(statusLine, responseHeaders, responseBody);
    }

    @Nonnull
    public static HttpResponse redirectBy302StatusCode(String location) {
        StatusLine statusLine = makeStatusLine(HttpStatus.FOUND);
        ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.put("Location", location);

        return new HttpResponse(statusLine, responseHeaders, ResponseBody.makeEmptyResponseBody());
    }

    @Nonnull
    public static HttpResponse redirectBy302StatusCode(String location, Map<String, String> additionalHeaders) {
        StatusLine statusLine = makeStatusLine(HttpStatus.FOUND);
        ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.put("Location", location);

        additionalHeaders.entrySet().stream()
                .forEach(e -> responseHeaders.put(e.getKey(), e.getValue()));

        return new HttpResponse(statusLine, responseHeaders, ResponseBody.makeEmptyResponseBody());
    }

    @Nonnull
    private static ResponseHeaders makeContentHeaders(String contentType, int contentLength) {
        ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.put("Content-Type", contentType);
        responseHeaders.put("Content-Length", Integer.toString(contentLength));

        return responseHeaders;
    }

    @Nonnull
    private static ResponseHeaders makeContentHeaders(String filePath) throws IOException, URISyntaxException {
        ResponseHeaders responseHeaders = new ResponseHeaders();
        String filenameExtension = StringUtils.getFilenameExtension(filePath);
        MimeType mimeType = MimeTypeUtil.findMimeTypeByFileExtension(filenameExtension);
        responseHeaders.put("Content-Type", mimeType.makeContentTypeValue());
        responseHeaders.put("Content-Length", FileIoUtils.loadFileFromClasspath(filePath).length);

        return responseHeaders;
    }

    @Nonnull
    private static StatusLine makeStatusLine(HttpStatus httpStatus) {
        String statusCode = Integer.toString(httpStatus.getValue());
        String reasonPhase = httpStatus.getReasonPhrase();
        return new StatusLine(new HttpProtocol("HTTP", "1.1"), statusCode, reasonPhase);
    }

    @Nonnull
    public byte[] makeHttpResponseBytes() {
        String statusLineString = statusLine.makeStatusLineString();
        String headersString = headers.makeHeadersString();

        String rawResponseString = statusLineString
                + headersString
                + "\r\n"
                + new String(body.getMessageBodyByteArray()); // TODO 수정 필요

        return rawResponseString.getBytes();
    }

    @Nonnull
    public static HttpResponse makeEmptyHttpResponse() {
        return new EmptyHttpResponse();
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        new StatusLine(new HttpProtocol("HTTP", "1.1"), Integer.toString(httpStatus.getValue()), httpStatus.getReasonPhrase());
    }

    static class EmptyHttpResponse extends HttpResponse {
        private EmptyHttpResponse() {
            super(StatusLine.makeEmptyStatusLine(), ResponseHeaders.makeEmptyResponseHeaders(), null);
        }
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public ResponseHeaders getHeaders() {
        return headers;
    }

    public ResponseBody getBody() {
        return body;
    }
}

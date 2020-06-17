package http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nonnull;

import utils.FileIoUtils;

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
    public static HttpResponse from2(@Nonnull HttpRequest httpRequest, HttpStatus httpStatus) {
        String filePath = FilePathUtils.makeFilePath(httpRequest.getRequestLine());
        MimeType mimeType = MimeTypeUtil.findMimeTypeByPath(httpRequest.getRequestLine().getPath());
        return from(filePath, httpStatus);
    }

    @Nonnull
    public static HttpResponse from(@Nonnull HttpRequest httpRequest, HttpStatus httpStatus) {
        String filePath = FilePathUtils.makeFilePath(httpRequest.getRequestLine());
        return from(filePath, httpStatus);
    }

    @Nonnull
    public static HttpResponse from(String filePath, HttpStatus httpStatus) {
        StatusLine statusLine = makeStatusLine(httpStatus);
        ResponseHeaders responseHeaders;
        ResponseBody responseBody;
        try {
            responseHeaders = makeContentHeaders(filePath);
            responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(filePath));
        } catch (IOException | URISyntaxException e) {
            return HttpResponse.makeErrorResponse();
        }

        return new HttpResponse(statusLine, responseHeaders, responseBody);
    }

    public static HttpResponse makeErrorResponse() {
        StatusLine statusLine = makeStatusLine(HttpStatus.INTERNAL_SERVER_ERROR);
        return new HttpResponse(statusLine, ResponseHeaders.makeEmptyResponseHeaders(), ResponseBody.makeEmptyResponseBody());
    }

    public static HttpResponse makeResponseWithHttpStatus(HttpStatus httpStatus) {
        StatusLine statusLine = makeStatusLine(httpStatus);
        return new HttpResponse(statusLine, ResponseHeaders.makeEmptyResponseHeaders(), ResponseBody.makeEmptyResponseBody());
    }

    @Nonnull
    public static HttpResponse from(byte[] responseBodyByteArray, HttpStatus httpStatus) {
        StatusLine statusLine = makeStatusLine(httpStatus);
        // TODO 여기도 contentType 과 length 는 역할을 따로 빼야 할 것 같음.

        ResponseHeaders responseHeaders = makeContentHeaders("text/html;charset=utf-8", responseBodyByteArray.length);
        ResponseBody responseBody = new ResponseBody(responseBodyByteArray);

        return new HttpResponse(statusLine, responseHeaders, responseBody);
    }

    @Nonnull
    public static HttpResponse redirectBy302StatusCode(@Nonnull String location) {
        return redirectBy302StatusCode(location, Collections.EMPTY_MAP);
    }

    @Nonnull
    public static HttpResponse redirectBy302StatusCode(@Nonnull String location, @Nonnull Map<String, String> additionalHeaders) {
        StatusLine statusLine = makeStatusLine(HttpStatus.FOUND);
        ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.put(ResponseHeaders.LOCATION, location);

        additionalHeaders.entrySet().stream()
                .forEach(e -> responseHeaders.put(e.getKey(), e.getValue()));

        return new HttpResponse(statusLine, responseHeaders, ResponseBody.makeEmptyResponseBody());
    }

    @Nonnull
    private static ResponseHeaders makeContentHeaders(String filePath) throws IOException, URISyntaxException {
        MimeType mimeType = MimeTypeUtil.findMimeTypeByPath(filePath);

        return makeContentHeaders(mimeType.makeContentTypeValue(), FileIoUtils.loadFileFromClasspath(filePath).length);
    }

    @Nonnull
    private static ResponseHeaders makeContentHeaders(String contentType, int contentLength) {
        ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.put(ResponseHeaders.CONTENT_TYPE, contentType);
        responseHeaders.put(ResponseHeaders.CONTENT_LENGTH, Integer.toString(contentLength));

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
                + new String(body.getMessageBodyByteArray());

        return rawResponseString.getBytes();
    }

    @Nonnull
    public static HttpResponse makeEmptyHttpResponse() {
        return new EmptyHttpResponse();
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

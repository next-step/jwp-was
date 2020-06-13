package http.handler;

import com.google.common.collect.Maps;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.handler.mapper.StaticResource;
import http.request.HttpRequest;
import http.response.StatusLine;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static http.common.HttpHeader.CONTENT_LENGTH_NAME;
import static http.common.HttpHeader.CONTENT_TYPE_HEADER_NAME;

@Slf4j
public class ExceptionHandler extends AbstractHandler {
    private HttpStatus httpStatus;

    public ExceptionHandler(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getContentType() {
        return StaticResource.HTML.getContentType();
    }

    @Override
    protected HttpHeaders getHttpHeaders(HttpRequest httpRequest, int length) {
        Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.put(CONTENT_LENGTH_NAME, String.valueOf(length));
        httpHeaders.put(CONTENT_TYPE_HEADER_NAME, getContentType());
        return new HttpHeaders(httpHeaders);
    }

    @Override
    public byte[] getHttpResponseBody(HttpRequest request) {
        return StatusLine.of(request.getProtocol(), getHttpStatus()).toString().getBytes();
    }
}

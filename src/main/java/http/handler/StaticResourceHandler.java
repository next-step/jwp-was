package http.handler;

import com.google.common.collect.Maps;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.handler.mapper.StaticResource;
import http.request.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static http.common.HttpHeader.CONTENT_LENGTH_NAME;
import static http.common.HttpHeader.CONTENT_TYPE_HEADER_NAME;

@Slf4j
public class StaticResourceHandler extends AbstractHandler {
    private final String path;
    private final StaticResource staticResource;

    public StaticResourceHandler(String path, StaticResource staticResource) {
        this.path = path;
        this.staticResource = staticResource;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.OK;
    }

    @Override
    public String getContentType() {
        return staticResource.getContentType();
    }

    @Override
    protected HttpHeaders getHttpHeaders(HttpRequest httpRequest, int contentLength) {
        Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.put(CONTENT_LENGTH_NAME, String.valueOf(contentLength));
        httpHeaders.put(CONTENT_TYPE_HEADER_NAME, getContentType());
        return new HttpHeaders(httpHeaders);
    }

    @Override
    public byte[] getHttpResponseBody(HttpRequest response) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(staticResource.getBasePath() + getPath());
    }
}

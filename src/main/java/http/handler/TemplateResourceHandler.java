package http.handler;

import com.google.common.collect.Maps;
import http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static http.HttpHeader.CONTENT_LENGTH_NAME;
import static http.HttpHeader.CONTENT_TYPE_NAME;

@Slf4j
public class TemplateResourceHandler extends AbstractHandler {
    protected String contentType;

    public TemplateResourceHandler(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public byte[] getHttpBody(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + path);
    }

    @Override
    protected HttpHeaders getHttpHeaders(int contentLength) {
        Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.put(CONTENT_LENGTH_NAME, String.valueOf(contentLength));
        httpHeaders.put(CONTENT_TYPE_NAME, this.contentType);

        return new HttpHeaders(httpHeaders);
    }
}

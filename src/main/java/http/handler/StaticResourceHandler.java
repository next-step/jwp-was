package http.handler;

import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
public class StaticResourceHandler extends TemplateResourceHandler {
    public StaticResourceHandler(String contentType) {
        super(contentType);
    }

    @Override
    public byte[] getHttpBody(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
    }
}

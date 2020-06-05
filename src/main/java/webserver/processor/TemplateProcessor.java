package webserver.processor;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateProcessor implements Processor {
    private static final String TEMPLATE_PREFIX = "./templates";

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return httpRequest.getPath()
                .endsWith(".html");
    }

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        String filePath = TEMPLATE_PREFIX + httpRequest.getPath();

        return HttpResponse.ok(
                ContentType.of(httpRequest.getExtension()),
                FileIoUtils.loadFileFromClasspath(filePath)
        );
    }
}

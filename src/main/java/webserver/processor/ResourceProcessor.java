package webserver.processor;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class ResourceProcessor implements Processor {
    private static final String STATIC_PREFIX = "./static";

    private static final List<String> RESOURCES_URL = Arrays.asList(
            "/js", "/images", "/fonts", "/css"
    );

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return RESOURCES_URL.stream()
                .anyMatch(url -> httpRequest.getPath().startsWith(url));
    }

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        String filePath = STATIC_PREFIX + httpRequest.getPath();

        return HttpResponse.ok(
                ContentType.of(httpRequest.getExtension()),
                FileIoUtils.loadFileFromClasspath(filePath)
        );
    }
}

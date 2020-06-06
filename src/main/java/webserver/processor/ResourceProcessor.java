package webserver.processor;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

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
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        String filePath = STATIC_PREFIX + httpRequest.getPath();

        httpResponse.updateType(ContentType.of(httpRequest.getExtension()));
        httpResponse.updateBody(FileIoUtils.loadFileFromClasspath(filePath));
    }
}

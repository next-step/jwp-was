package webserver.processor;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.util.Arrays;
import java.util.List;

public class TemplateProcessor implements Processor {
    private static final String TEMPLATE_PREFIX = "./templates";

    private static final List<String> TEMPLATE_URL = Arrays.asList(
            ".html", "ico"
    );

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return TEMPLATE_URL.stream()
                .anyMatch(url -> httpRequest.getPath().endsWith(url));
    }

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        String filePath = TEMPLATE_PREFIX + httpRequest.getPath();

        httpResponse.updateType(ContentType.of(httpRequest.getExtension()));
        httpResponse.updateBody(FileIoUtils.loadFileFromClasspath(filePath));
    }
}

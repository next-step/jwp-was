package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class ResourceProcessor implements Processor {
    private static final List<String> RESOURCES_URL = Arrays.asList(
            "/js", "/images", "/fonts", "/css"
    );

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return RESOURCES_URL.stream()
                .anyMatch(url -> httpRequest.getPath().startsWith(url));
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return null;
    }
}

package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

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
    public HttpResponse process(HttpRequest httpRequest) {
        String filePath = "./static" + httpRequest.getPath();
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new HttpResponse(200, body);
    }
}

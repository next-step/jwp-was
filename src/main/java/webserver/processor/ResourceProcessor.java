package webserver.processor;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.Arrays;
import java.util.List;

public class ResourceProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(ResourceProcessor.class);
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
    public void process(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String filePath = STATIC_PREFIX + httpRequest.getPath();

        try {
            httpResponse.updateBody(FileIoUtils.loadFileFromClasspath(filePath));
            httpResponse.updateType(ContentType.of(httpRequest.getExtension()));
        } catch (Exception e) {
            logger.error("File request that not exists : " + filePath);
            httpResponse.updateStatus(StatusCode.NOT_FOUND);
        }
    }
}

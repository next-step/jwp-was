package webserver.processor;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class TemplateProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(TemplateProcessor.class);
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
    public void process(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String filePath = TEMPLATE_PREFIX + httpRequest.getPath();

        try {
            httpResponse.updateBody(FileIoUtils.loadFileFromClasspath(filePath));
            httpResponse.updateType(ContentType.of(httpRequest.getExtension()));
        } catch (Exception e) {
            logger.error("File request that not exists : " + filePath);
            httpResponse.updateStatus(StatusCode.NOT_FOUND);
        }
    }
}

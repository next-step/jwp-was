package controller;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class ResourceController extends GetController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private static final String TEMPLATE_PATH = "./templates";

    private static final String STATIC_RESOURCE_PATH = "./static";

    private static final Set<String> ALLOWED_RESOURCES = Sets.newHashSet("/index.html", "\\/.*\\.html", "\\/.*\\.css", "\\/.*\\.js");

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        String pathStr = httpRequest.getPathStr();
        try {
            HttpResponse httpResponse = HttpResponseFactory.response202(FileIoUtils.loadFileFromClasspath(getStaticFilePath(pathStr)));
            String contentType = httpRequest.getContentType();
            if (!Strings.isNullOrEmpty(contentType)) {
                httpResponse.setContentType(contentType);
            }
            return httpResponse;
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            throw new ResourceException(pathStr);
        }
    }

    public boolean canServe(HttpRequest httpRequest) {
        return ALLOWED_RESOURCES.stream()
                .anyMatch(httpRequest::matchPath);
    }

    private String getStaticFilePath(String pathStr) {
        return STATIC_RESOURCE_PATH + pathStr;
    }

    private String getHtmlFilePath(String str) {
        return TEMPLATE_PATH + str;
    }

}

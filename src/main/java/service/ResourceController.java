package service;

import com.google.common.collect.Sets;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class ResourceController extends GetController {

    private static final String TEMPLATE_PATH = "./templates";

    private static final String STATIC_RESOURCE_PATH = "./static";

    private static final Set<String> ALLOWED_RESOURCES = Sets.newHashSet("/index.html", "\\/.*\\.html", "\\/.*\\.css");

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        String pathStr = httpRequest.getPathStr();
        try {
            if (pathStr.contains(".html")) { // TODO refactoring
                HttpResponse httpResponse = new HttpResponse(FileIoUtils.loadFileFromClasspath(getHtmlFilePath(pathStr)), "202");
                httpResponse.setTextHtml();
                return httpResponse;
            } else if (pathStr.contains(".css")) {
                HttpResponse httpResponse = new HttpResponse(FileIoUtils.loadFileFromClasspath(getStaticFilePath(pathStr)), "202");
                httpResponse.setTextCss();
                return httpResponse;
            } else {
                HttpResponse httpResponse = new HttpResponse(FileIoUtils.loadFileFromClasspath(getStaticFilePath(pathStr)), "202");
                return httpResponse;
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e); // TODO custom exception
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

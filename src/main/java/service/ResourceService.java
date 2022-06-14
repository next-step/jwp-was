package service;

import com.google.common.collect.Sets;
import utils.FileIoUtils;
import webserver.request.Headers;
import webserver.request.RequestLine;
import webserver.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class ResourceService extends GetService {

    private static final String TEMPLATE_PATH = "./templates";

    private static final String STATIC_RESOURCE_PATH = "./static";

    private static final Set<String> ALLOWED_RESOURCES = Sets.newHashSet("/index.html", "\\/.*\\.html", "\\/.*\\.css");

    @Override
    public Response doGet(RequestLine requestLine) {
        String pathStr = requestLine.getPathStr();
        try {
            if (pathStr.contains(".html")) { // TODO refactoring
                Response response = new Response(FileIoUtils.loadFileFromClasspath(getHtmlFilePath(pathStr)), "202", Headers.empty());
                response.setTextHtml();
                return response;
            } else if (pathStr.contains(".css")) {
                Response response = new Response(FileIoUtils.loadFileFromClasspath(getStaticFilePath(pathStr)), "202", Headers.empty());
                response.setTextCss();
                return response;
            } else {
                Response response = new Response(FileIoUtils.loadFileFromClasspath(getStaticFilePath(pathStr)), "202", Headers.empty());
                return response;
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e); // TODO custom exception
        }

    }

    private String getStaticFilePath(String pathStr) {
        return STATIC_RESOURCE_PATH + pathStr;
    }

    private String getHtmlFilePath(String str) {
        return TEMPLATE_PATH + str;
    }

    @Override
    public boolean canServe(RequestLine requestLine) {
        return ALLOWED_RESOURCES.stream()
                .anyMatch(requestLine::matchPath);
    }
}

package service;

import com.google.common.collect.Sets;
import utils.FileIoUtils;
import webserver.request.RequestLine;
import webserver.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Set;

public class ResourceService extends GetService {

    private static final String staticResourcePath = "./templates";

    private static final Set<String> ALLOWED_RESOURCES = Sets.newHashSet("/index.html", "/user/form.html", "/user/login.html", "/user/login_failed.html");

    @Override
    public Response doGet(RequestLine requestLine) {
        String filePath = getFilePath(requestLine.getPathStr());
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e); // TODO custom exception
        }

        return new Response(body, "202", null, new HashMap<>());
    }

    private String getFilePath(String str) {
        return staticResourcePath + str;
    }

    @Override
    public boolean canServe(RequestLine requestLine) {
        return ALLOWED_RESOURCES.stream()
                .anyMatch(requestLine::matchPath);
    }
}

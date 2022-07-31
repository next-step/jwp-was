package webserver.http.domain.controller;

import utils.FileIoUtils;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

import static webserver.http.domain.request.Method.GET;

public class RootController implements Controller {
    private static final String STATIC_RESOURCE_DEFAULT_DIRECTORY = "./static";
    private static final String DEFAULT_RESOURCE_PATH = "/index.html";
    private static final String DEFAULT_RESOURCE_PULL_PATH = STATIC_RESOURCE_DEFAULT_DIRECTORY + DEFAULT_RESOURCE_PATH;

    @Override
    public boolean requires(Request request) {
        return request.hasMethod(GET) &&
                request.hasPath("/") &&
                FileIoUtils.hasFile(DEFAULT_RESOURCE_PULL_PATH);
    }

    @Override
    public Response handle(Request request) {
        byte[] bytes = FileIoUtils.loadFileFromClasspath(DEFAULT_RESOURCE_PULL_PATH);
        Response response = Response.ok();
        response.addHeader("Content-Type", "text/html");
        response.addBody(bytes);
        return response;
    }
}

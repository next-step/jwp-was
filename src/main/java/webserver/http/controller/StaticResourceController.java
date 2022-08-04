package webserver.http.controller;

import webserver.http.domain.ContentType;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.utils.FileIoUtils;

import static webserver.http.domain.Headers.CONTENT_TYPE;

public class StaticResourceController implements Controller{
    private static final String STATIC_RESOURCE_DEFAULT_DIRECTORY = "./static";
    @Override
    public boolean requires(Request request) {
        return request.hasMethod(Method.GET) &&
                FileIoUtils.hasFile(STATIC_RESOURCE_DEFAULT_DIRECTORY + request.getPath());
    }

    @Override
    public Response handle(Request request) {
        String resourcePath = request.getPath();
        byte[] bytes = FileIoUtils.loadFileFromClasspath(STATIC_RESOURCE_DEFAULT_DIRECTORY + resourcePath);
        Response response = Response.ok();
        ContentType contentType = ContentType.from(resourcePath);
        response.addHeader(CONTENT_TYPE, contentType.getHeader());
        response.addBody(bytes);
        return response;
    }
}

package http.handler;

import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.handler.mapper.StaticResource;
import http.request.HttpRequest;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
public abstract class AbstractHandler implements Handler {
    public static final String TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";

    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.setStatusLine(request.getProtocol(), getHttpStatus());

        byte[] httpBody = getHttpResponseBody(request);
        response.setHttpEntity(getHttpHeaders(request, httpBody.length), new String(httpBody));
        response.write();
    }

    public abstract String getPath();

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.OK;
    }

    @Override
    public String getContentType() {
        return StaticResource.HTML.getContentType();
    }

    protected abstract HttpHeaders getHttpHeaders(HttpRequest httpRequest, int length);

    @Override
    public byte[] getHttpResponseBody(HttpRequest response) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + getPath());
    }
}

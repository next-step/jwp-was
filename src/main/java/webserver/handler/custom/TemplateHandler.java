package webserver.handler.custom;

import http.request.Request;
import http.request.headers.Headers;
import http.response.headers.ContentType;
import http.response.responseline.HttpStatus;
import http.response.Response;
import http.response.body.ResponseBody;
import utils.FileIoUtils;
import webserver.handler.Handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TemplateHandler implements Handler {
    private static final String PREFIX_TEMPLATES = "./templates";

    @Override
    public boolean isSameUrl(String url) {
        return false;
    }

    @Override
    public Response work(Request request) throws URISyntaxException, IOException {
        ResponseBody body = new ResponseBody(getBody(request));
        return new Response(HttpStatus.OK, ContentType.HTML, getHeaders(body), body);
    }

    private Headers getHeaders(ResponseBody body){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", String.valueOf(body.getBody().length));
        headers.put("Content-Type", ContentType.HTML.getContentType());
        return new Headers(headers);
    }

    private byte[] getBody(Request request) throws IOException, URISyntaxException {
        String filePath = PREFIX_TEMPLATES + request.getUrl();
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    @Override
    public String getUrl() {
        return null;
    }
}

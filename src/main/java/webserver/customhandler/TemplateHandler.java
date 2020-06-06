package webserver.customhandler;

import http.request.Request;
import http.request.headers.Headers;
import http.request.headers.Headers2;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import http.response.ResponseBody;
import utils.FileIoUtils;
import webserver.Handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    private Headers2 getHeaders(ResponseBody body){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length: ", String.valueOf(body.getBody().length));
        headers.put("Content-Type: ", ContentType.HTML.getContentType());

        return new Headers2(headers);
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

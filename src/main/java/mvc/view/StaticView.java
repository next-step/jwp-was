package mvc.view;

import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class StaticView implements View {

    private final static String STATIC_PATH = "./static";

    @Override
    public void render(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.responseOk(
                HttpHeader.from(Collections.singletonMap(
                        HttpHeader.CONTENT_TYPE, String.format("text/%s; charset=utf-8",
                        fileExtension(request)))), FileIoUtils.loadFileFromClasspath(staticPath(request))
        );
    }

    private String fileExtension(HttpRequest request) {
        String path = request.getPath();
        return path.substring(request.getPath().lastIndexOf('.') + 1);
    }

    private String staticPath(HttpRequest request) {
        return STATIC_PATH + request.getPath();
    }
}

package webserver.http.response.view;

import utils.FileIoUtils;
import webserver.http.response.ContentType;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class StaticResourceViewRenderer extends AbstractViewRenderer {

    private static final String STATIC_RESOURCE_PATH_BASE = "./static";

    private String path;

    public StaticResourceViewRenderer(HttpResponse httpResponse, String path) {
        super(httpResponse);
        this.path = path;
    }

    @Override
    public void render() {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(STATIC_RESOURCE_PATH_BASE + path);
            if (path.contains(".css")) {
                httpResponse.setContentType(ContentType.CSS);
            }
            responseHeader(body.length);
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

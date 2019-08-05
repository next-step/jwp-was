package webserver.http.response.view;

import utils.FileIoUtils;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class TemplateResourceViewRenderer extends AbstractViewRenderer {

    private static final String TEMPLATE_RESOURCE_PATH_BASE = "./templates";

    private String path;

    public TemplateResourceViewRenderer(HttpResponse httpResponse, String path) {
        super(httpResponse);
        this.path = path;
    }

    @Override
    public void render() {

        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(TEMPLATE_RESOURCE_PATH_BASE + path);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        responseHeader(body.length);
        responseBody(body);
    }
}

package webserver.http.response.view;

import utils.FileIoUtils;
import webserver.http.response.ContentType;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

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

        try {
            if (httpResponse.getRedirectUrl() != null) {
                httpResponse.setHttpStatus(HttpStatus.REDIRECT);
                httpResponse.addHeader("Location", httpResponse.getRedirectUrl());
                writeHeader(0);
                writeBody(new byte[0]);
            } else {
                httpResponse.setHttpStatus(HttpStatus.OK);
                byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATE_RESOURCE_PATH_BASE + path);
                httpResponse.setContentType(ContentType.HTML_UTF_8);
                writeHeader(body.length);
                writeBody(body);
                flush();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

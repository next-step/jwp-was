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
public class StaticResourceViewRenderer extends AbstractViewRenderer {

    private String path;
    private String prefix;

    public StaticResourceViewRenderer(HttpResponse httpResponse, String path, String prefix) {
        super(httpResponse);
        this.path = path;
        this.prefix = prefix;
    }

    @Override
    public void render() {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("." + prefix + path);
            if (path.contains(".css")) {
                httpResponse.setContentType(ContentType.CSS);
            }
            httpResponse.setHttpStatus(HttpStatus.OK);
            writeHeader(body.length);
            writeBody(body);
            flush();
        } catch (IOException | URISyntaxException e) {
            witeErrorPage(e);
        }
    }
}

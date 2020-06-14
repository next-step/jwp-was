package http.controller;

import http.HttpRequest;
import http.HttpResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public abstract class StaticController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

    private static final String STATIC_CONTENT_PATH = "./static";

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try {
            final String path = request.getPath();
            final byte[] body = FileIoUtils.loadFileFromClasspath(STATIC_CONTENT_PATH + path);
            response.responseStatic(body, getContentType(path));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    protected abstract String getContentType(String path);

    protected String getExtension(String path) {
        int position = path.lastIndexOf('.');
        return path.substring(position + 1);
    }
}

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
public class HTMLController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(HTMLController.class);

    private static final String TEMPLATE_CONTENT_PATH = "./templates";

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try {
            final byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATE_CONTENT_PATH + request.getPath());
            response.responseHTML(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}

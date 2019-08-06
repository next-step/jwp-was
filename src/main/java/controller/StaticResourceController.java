package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.Request;
import webserver.Response;

import java.util.Arrays;
import java.util.List;

public class StaticResourceController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceController.class);

    private static final String STATIC_PATH = "./static";
    private static final List<String> staticResources = Arrays.asList("/css", "/fonts", "/js", "/images");

    public static boolean isMapping(Request request) {
        return staticResources.stream()
                .anyMatch(request::containPath);
    }

    @Override
    void doGet(Request request, Response response) throws Exception {
        logger.info(request.getAccept());
        String contentType = request.getAccept();

        byte[] body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + request.getPath());
        response.ok(body, contentType);
    }

    @Override
    void doPost(Request request, Response response) throws Exception {
        response.notFound();
    }
}

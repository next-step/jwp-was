package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;

import java.util.Arrays;
import java.util.List;

import static webserver.HttpHeaders.ACCEPT;

public class StaticResourceServlet implements HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceServlet.class);

    private static final String STATIC_PATH = "./static";
    private static final List<String> staticResources = Arrays.asList("/css", "/fonts", "/js", "/images");

    @Override
    public boolean isMapping(Request request) {
        return staticResources.stream()
                .anyMatch(path -> request.getPath().contains(path));
    }

    @Override
    public Response service(Request request) throws Exception {
        logger.info(request.getHeader(ACCEPT));

        String contentType = request.getHeader(ACCEPT).split(",")[0];

        byte[] body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + request.getPath());
        return Response.ok(body, contentType);
    }
}

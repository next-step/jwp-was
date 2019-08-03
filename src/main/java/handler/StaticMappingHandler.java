package handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;
import webserver.RequestHandler;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class StaticMappingHandler implements RequestStrategy {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public HttpResponse request(HttpRequest httpRequest) {
        try {
            return HttpResponse.css(FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getRequestLine().getPath().trim()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("StaticMappingHandler request failed: ", e);
        }
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        String path = httpRequest.getRequestLine().getPath();
        return path.contains(".css") || path.contains(".js");
    }
}
